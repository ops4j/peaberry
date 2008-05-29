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

import static org.ops4j.peaberry.Peaberry.osgiModule;

import java.lang.reflect.Method;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.testng.ISuite;
import org.testng.ITestRunnerFactory;
import org.testng.TestRunner;
import org.testng.reporters.TestHTMLReporter;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Support OSGi classloading of TestNG testcases.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiTestRunnerFactory
    implements ITestRunnerFactory {

  final Bundle testBundle;

  public OSGiTestRunnerFactory(Bundle testBundle) {
    this.testBundle = testBundle;
  }

  public TestRunner newTestRunner(ISuite suite, XmlTest test) {

    GuiceObjectFactory.setInjector(null);

    if (suite.getName().startsWith("OSGi")) {

      for (XmlClass xmlClazz : test.getXmlClasses()) {
        String name = xmlClazz.getSupportClass().getName();
        try {
          // reload testcase class using test bundle
          xmlClazz.setClass(testBundle.loadClass(name));
        } catch (ClassNotFoundException e) {}
      }

      // create custom injector for each testcase in turn
      GuiceObjectFactory.setInjector(getTestInjector(test));
    }

    TestRunner runner = new TestRunner(suite, test, false);
    runner.addListener(new TestHTMLReporter());

    return runner;
  }

  private Injector getTestInjector(final XmlTest test) {

    return Guice.createInjector(new Module() {

      @SuppressWarnings("unchecked")
      public void configure(Binder binder) {

        boolean manualSetup = false;
        for (XmlClass xmlClazz : test.getXmlClasses()) {
          Class clazz = xmlClazz.getSupportClass();

          // this forces guice to inject our testcase
          binder.bind(clazz);

          // look for a custom setup method (with its own bindings)
          Method setup;
          try {
            setup = clazz.getMethod("setup", Binder.class, BundleContext.class);
            manualSetup = true;
            try {
              setup.invoke(null, binder, testBundle.getBundleContext());
            } catch (Exception e) {
              e.printStackTrace();
            }
          } catch (NoSuchMethodException e) {}
        }

        if (!manualSetup) {
          // no custom setup, just use the standard OSGi module
          binder.install(osgiModule(testBundle.getBundleContext()));
        }
      }
    });
  }
}
