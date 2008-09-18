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

import static org.apache.felix.main.Main.loadConfigProperties;
import static org.testng.TestNGCommandLineArgs.parseCommandLine;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.main.AutoActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;
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
      e.printStackTrace();
    }
  }

  private static final Felix FELIX;

  static {
    final Map config = new StringMap(loadConfigProperties(), false);

    final List autoActivatorList = new ArrayList();
    autoActivatorList.add(new AutoActivator(config));

    FELIX = new Felix(config, autoActivatorList);
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

    setDefaultSuiteName("Peaberry");
    setDefaultTestName("Unit Tests");

    setXmlSuites(new ArrayList());

    try {

      setTestClasses(installTestBundles());

      super.run();

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

  private Class[] installTestBundles() {
    final Collection<Class> clazzes = new HashSet();

    final BundleContext ctx = FELIX.getBundleContext();
    final File testBundleDir = new File(ctx.getProperty("test.bundle.dir"));

    System.out.println();

    for (final File f : testBundleDir.listFiles()) {
      if (f.getName().endsWith(".jar")) {
        try {

          final String location = f.toURI().toASCIIString();
          final Bundle bundle = ctx.installBundle(location);

          System.out.println("[Director] Starting: " + bundle);

          bundle.start();

          clazzes.addAll(installTestCases(bundle));

          System.out.println();

        } catch (final BundleException e) {
          System.err.println("Error installing test bundle: " + f + " message: " + e.getMessage());
        }
      }
    }

    return clazzes.toArray(new Class[clazzes.size()]);
  }

  private Collection installTestCases(final Bundle bundle) {
    final Collection clazzes = new HashSet();

    final Enumeration i = bundle.findEntries("/", "*Tests.class", true);

    while (i != null && i.hasMoreElements()) {

      final String path = ((URL) i.nextElement()).getPath();
      final String name = path.substring(1, path.length() - 6).replace('/', '.');

      System.out.println("[Director]  Loading: " + name);

      try {
        clazzes.add(bundle.loadClass(name));
      } catch (final ClassNotFoundException e) {
        System.err.println("Error loading test class: " + name + " message: " + e.getMessage());
      }
    }

    return clazzes;
  }

  private static volatile PackageAdmin PACKAGE_ADMIN;

  public static BundleContext findContext(final Class clazz) {

    if (null == PACKAGE_ADMIN) {
      final BundleContext ctx = FELIX.getBundleContext();
      final ServiceReference ref = ctx.getServiceReference(PackageAdmin.class.getName());
      PACKAGE_ADMIN = (PackageAdmin) ctx.getService(ref);
    }

    return PACKAGE_ADMIN.getBundle(clazz).getBundleContext();
  }
}
