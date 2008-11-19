/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry.test;

import static org.apache.felix.framework.util.FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP;
import static org.apache.felix.main.Main.loadConfigProperties;
import static org.testng.TestNGCommandLineArgs.parseCommandLine;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;
import org.testng.IObjectFactory;
import org.testng.TestNG;
import org.testng.TestNGException;

/**
 * TestNG launcher that runs tests as OSGi bundles on the Felix framework.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@SuppressWarnings("unchecked")
public final class Director
    extends TestNG {

  public static void main(final String[] args) {
    final TestNG testNG = new Director();

    testNG.configure(checkConditions(parseCommandLine(args)));

    try {
      testNG.run();
    } catch (final TestNGException e) {
      // /CLOVER:OFF
      e.printStackTrace();
      // /CLOVER:ON
    }
  }

  // globally cached system bundle
  private static final Felix FELIX;

  static {
    final Map config = loadConfigProperties();

    final List autoActivatorList = new ArrayList();
    autoActivatorList.add(new AutoActivator(config));

    config.put(SYSTEMBUNDLE_ACTIVATORS_PROP, autoActivatorList);

    FELIX = new Felix(config);
  }

  public static class ObjectFactory
      implements IObjectFactory {

    private static final long serialVersionUID = 1L;

    // improve reporting by unwrapping certain wrapped exceptions, like ITE
    public Object newInstance(final Constructor ctor, final Object... args) {
      // /CLOVER:OFF
      try {
        return ctor.newInstance(args);
      } catch (final InvocationTargetException e) {
        throw new TestNGException(e.getCause());
      } catch (final Exception e) {
        throw new TestNGException(e);
      }
      // /CLOVER:ON
    }
  }

  @Override
  public void run() {

    System.out.println("=====================");
    System.out.println("Start Felix container");
    System.out.println("=====================");

    try {
      FELIX.start();
    } catch (final BundleException e) {
      // /CLOVER:OFF
      throw new RuntimeException(e);
      // /CLOVER:ON
    }

    setDefaultSuiteName("Peaberry");
    setDefaultTestName("UnitTests");

    // clear out dummy command-line tests
    setObjectFactory(ObjectFactory.class);
    setXmlSuites(new ArrayList());

    // load testcase classes from the appropriate bundles
    setTestClasses(installTestCases(installTestBundles()));

    try {

      super.run(); // run tests!

    } catch (final Exception e) {
      // /CLOVER:OFF
      throw new RuntimeException(e);
      // /CLOVER:ON
    } finally {

      System.out.println("=====================");
      System.out.println("Stop Felix container ");
      System.out.println("=====================");

      try {
        FELIX.stop();
      } catch (final BundleException e) {
        // /CLOVER:OFF
        throw new RuntimeException(e);
        // /CLOVER:ON
      }
    }
  }

  private Bundle[] installTestBundles() {

    final BundleContext ctx = FELIX.getBundleContext();
    final Collection<Bundle> bundles = new HashSet();

    final File testBundleDir = new File(ctx.getProperty("test.bundle.dir"));

    for (final File f : testBundleDir.listFiles()) {
      // /CLOVER:OFF
      if (f.getName().endsWith(".jar")) {
        // /CLOVER:ON
        try {
          final String location = f.toURI().toASCIIString();
          bundles.add(ctx.installBundle(location));
        } catch (final BundleException e) {
          // /CLOVER:OFF
          System.err.println("Error installing test bundle: " + f + " message: " + e.getMessage());
          // /CLOVER:ON
        }
      }
    }

    return bundles.toArray(new Bundle[bundles.size()]);
  }

  private Class[] installTestCases(final Bundle[] bundles) {
    final Collection<Class> clazzes = new HashSet();

    System.out.println();

    for (final Bundle b : bundles) {
      System.out.println("[Director] Starting: " + b);
      try {
        clazzes.addAll(installTestCases(b));
        b.start();
      } catch (final BundleException e) {
        // /CLOVER:OFF
        System.err.println("Error starting test bundle: " + b + " message: " + e.getMessage());
        // /CLOVER:ON
      }
      System.out.println();
    }

    return clazzes.toArray(new Class[clazzes.size()]);
  }

  private Collection installTestCases(final Bundle bundle) {
    final Collection clazzes = new HashSet();

    final Enumeration i = bundle.findEntries("/", "*Tests.class", true);
    while (i != null && i.hasMoreElements()) {

      // convert jar entry path into dotted class name...
      final String path = ((URL) i.nextElement()).getPath();
      final String name = path.substring(1, path.length() - 6).replace('/', '.');

      System.out.println("[Director]  Loading: " + name);

      try {
        clazzes.add(bundle.loadClass(name));
      } catch (final ClassNotFoundException e) {
        // /CLOVER:OFF
        System.err.println("Error loading test class: " + name + " message: " + e.getMessage());
        // /CLOVER:ON
      }
    }

    return clazzes;
  }

  private static volatile PackageAdmin PACKAGE_ADMIN;

  public static BundleContext findContext(final Class clazz) {

    if (null == PACKAGE_ADMIN) {
      // lazy-load the framework package-admin service
      final BundleContext ctx = FELIX.getBundleContext();
      final ServiceReference ref = ctx.getServiceReference(PackageAdmin.class.getName());
      PACKAGE_ADMIN = (PackageAdmin) ctx.getService(ref);
    }

    // find the bundle which loaded the given testcase class
    return PACKAGE_ADMIN.getBundle(clazz).getBundleContext();
  }
}
