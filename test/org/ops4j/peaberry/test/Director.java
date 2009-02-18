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
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.testng.IObjectFactory;
import org.testng.TestNG;
import org.testng.TestNGException;
import org.testng.xml.XmlSuite;

/**
 * TestNG launcher that runs tests as OSGi bundles on the Felix framework.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Director
    extends TestNG {

  public static void main(final String[] args) {
    final TestNG testNG = new Director();

    testNG.configure(checkConditions(parseCommandLine(args)));

    try {
      testNG.run();
    } catch (final TestNGException e) {
      e.printStackTrace();
    }
  }

  // globally cached system bundle
  private static final Felix FELIX;

  static {
    final Properties config = loadConfigProperties();

    final List<BundleActivator> autoActivatorList = new ArrayList<BundleActivator>();
    autoActivatorList.add(new AutoActivator(config));

    config.put(SYSTEMBUNDLE_ACTIVATORS_PROP, autoActivatorList);

    FELIX = new Felix(config);
  }

  // customised to unravel ITEs
  public static class ObjectFactory
      implements IObjectFactory {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public Object newInstance(final Constructor ctor, final Object... args) {
      try {
        return ctor.newInstance(args);
      } catch (final InvocationTargetException e) {
        throw new TestNGException(e.getCause());
      } catch (final Exception e) {
        throw new TestNGException(e);
      }
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
      throw new RuntimeException(e);
    }

    final String name = FELIX.getBundleContext().getProperty("test.suite.name");
    setDefaultSuiteName(name == null ? "${test.suite.name}" : name);
    setDefaultTestName("UnitTests");

    // clear out dummy command-line tests
    setXmlSuites(new ArrayList<XmlSuite>());
    setObjectFactory(ObjectFactory.class);

    // load testcase classes from the appropriate bundles
    setTestClasses(installTestCases(installTestBundles()));

    try {

      super.run(); // run tests!

    } catch (final Exception e) {
      throw new RuntimeException(e);
    } finally {

      System.out.println("=====================");
      System.out.println("Stop Felix container ");
      System.out.println("=====================");

      try {
        FELIX.stop();
      } catch (final BundleException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Bundle[] installTestBundles() {

    final BundleContext ctx = FELIX.getBundleContext();
    final Collection<Bundle> bundles = new HashSet<Bundle>();

    final File testBundleDir = new File(ctx.getProperty("test.bundle.dir"));

    for (final File f : testBundleDir.listFiles()) {
      if (f.getName().endsWith(".jar")) {
        try {
          final String location = f.toURI().toASCIIString();
          bundles.add(ctx.installBundle(location));
        } catch (final BundleException e) {
          System.err.println("Error installing test bundle: " + f + " message: " + e.getMessage());
        }
      }
    }

    return bundles.toArray(new Bundle[bundles.size()]);
  }

  private Class<?>[] installTestCases(final Bundle... bundles) {
    final Collection<Class<?>> clazzes = new HashSet<Class<?>>();

    System.out.println();

    for (final Bundle b : bundles) {
      System.out.println("[Director] Starting: " + b);
      try {
        b.start();
        clazzes.addAll(installTestCases(b));
      } catch (final BundleException e) {
        System.err.println("Error starting test bundle: " + b + " message: " + e.getMessage());
      }
      System.out.println();
    }

    return clazzes.toArray(new Class[clazzes.size()]);
  }

  private Collection<Class<?>> installTestCases(final Bundle bundle) {
    final Collection<Class<?>> clazzes = new HashSet<Class<?>>();

    final Enumeration<?> i = bundle.findEntries("/", "*Tests.class", true);
    while (i != null && i.hasMoreElements()) {

      // convert jar entry path into dotted class name...
      final String path = ((URL) i.nextElement()).getPath();
      final String name = path.substring(1, path.length() - 6).replace('/', '.');

      System.out.println("[Director]  Loading: " + name);

      final Class<?> clazz;

      try {
        clazz = bundle.loadClass(name);
      } catch (final ClassNotFoundException e) {
        System.err.println("Error loading test class: " + name + " message: " + e.getMessage());
        continue;
      }

      clazzes.add(clazz); // add the test class to the current suite

      try {
        // store bundle reference in testcase class using static method
        clazz.getMethod("setBundle", Bundle.class).invoke(null, bundle);
      } catch (final Exception e) {}
    }

    return clazzes;
  }
}
