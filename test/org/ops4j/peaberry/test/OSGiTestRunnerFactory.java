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

import static org.ops4j.peaberry.Peaberry.getBundleModule;

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
          xmlClazz.setClass(testBundle.loadClass(name));
        } catch (ClassNotFoundException e) {}
      }

      GuiceObjectFactory.setInjector(getTestInjector(test));
    }

    TestRunner runner = new TestRunner(suite, test, false);
    runner.addListener(new TestHTMLReporter());

    return runner;
  }

  private Injector getTestInjector(final XmlTest test) {

    Module testcaseModule = new Module() {
      @SuppressWarnings("unchecked")
      public void configure(Binder binder) {
        boolean manualSetup = false;
        for (XmlClass xmlClazz : test.getXmlClasses()) {
          Class c = xmlClazz.getSupportClass();
          binder.bind(c);
          try {
            Method m = c.getMethod("setup", Binder.class, BundleContext.class);
            m.invoke(null, binder, testBundle.getBundleContext());
            manualSetup = true;
          } catch (Exception e) {}
        }
        if (!manualSetup) {
          binder.install(getBundleModule(testBundle.getBundleContext()));
        }
      }
    };

    return Guice.createInjector(testcaseModule);
  }
}
