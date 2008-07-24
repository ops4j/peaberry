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
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class OSGiTestRunnerFactory
    implements ITestRunnerFactory {

  static volatile Bundle bundle;

  public static void setBundle(final Bundle bundle) {
    OSGiTestRunnerFactory.bundle = bundle;
  }

  public TestRunner newTestRunner(final ISuite suite, final XmlTest test) {
    GuiceObjectFactory.setInjector(null);

    if (suite.getName().startsWith("OSGi")) {

      for (final XmlClass xmlClazz : test.getXmlClasses()) {
        final String name = xmlClazz.getSupportClass().getName();
        try {
          // reload testcase class using test bundle
          xmlClazz.setClass(bundle.loadClass(name));
        } catch (final ClassNotFoundException e) {}
      }

      // create custom injector for each OSGi testcase in turn
      GuiceObjectFactory.setInjector(getOSGiTestInjector(test));
    }

    final TestRunner runner = new TestRunner(suite, test, false);
    runner.addListener(new TestHTMLReporter());

    return runner;
  }

  private Injector getOSGiTestInjector(final XmlTest test) {
    return Guice.createInjector(new Module() {

      public void configure(final Binder binder) {
        for (final XmlClass xmlClazz : test.getXmlClasses()) {
          try {
            final Class<?> clazz = xmlClazz.getSupportClass();
            final Method configure = clazz.getMethod("configure", Binder.class);
            configure.invoke(null, binder);
          } catch (final Exception e) {
            e.printStackTrace();
          }
        }

        binder.install(osgiModule(bundle.getBundleContext()));
      }
    });
  }
}
