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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.main.AutoActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.testng.ITestRunnerFactory;
import org.testng.TestNG;
import org.testng.TestNGException;

/**
 * TestNG launcher that runs tests inside the Felix framework.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class OSGiTestNG
    extends TestNG {

  @Override
  public void run() {

    System.out.println("=====================");
    System.out.println("Start Felix container");
    System.out.println("=====================");

    @SuppressWarnings("unchecked")
    final Map config = new StringMap(loadConfigProperties(), false);

    final List<Object> autoActivatorList = new ArrayList<Object>();
    autoActivatorList.add(new AutoActivator(config));

    final Felix felix = new Felix(config, autoActivatorList);

    try {
      felix.start();
    } catch (final BundleException e) {
      throw new RuntimeException(e);
    }

    try {

      // currently tests run inside one bundle, adding multiple test bundles
      // and scripting is something to be looked into for integration tests
      final String testcasesURL = "file:" + System.getProperty("testcases.jar");
      final Bundle testBundle = felix.getBundleContext().installBundle(testcasesURL);

      testBundle.start();

      // enable support for OSGi classloading of testcase classes
      final Class<?> runnerFactoryClz = testBundle.loadClass(OSGiTestRunnerFactory.class.getName());
      final ITestRunnerFactory runnerFactory = (ITestRunnerFactory) runnerFactoryClz.newInstance();
      final Method setBundleMethod = runnerFactoryClz.getMethod("setBundle", Bundle.class);
      setBundleMethod.invoke(runnerFactory, testBundle);
      setTestRunnerFactory(runnerFactory);

      // use Guice to create test instances
      final Class<?> objectFactoryClz = testBundle.loadClass(GuiceObjectFactory.class.getName());
      setObjectFactory(objectFactoryClz);

      super.run();

    } catch (final Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        felix.stop();
      } catch (final BundleException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static void main(final String[] args) {

    // Enable detailed tracing when testing
    final Logger rootLogger = Logger.getLogger("");
    for (final Handler h : rootLogger.getHandlers()) {
      h.setLevel(Level.FINE);
    }
    rootLogger.setLevel(Level.FINE);

    @SuppressWarnings("unchecked")
    final Map params = checkConditions(parseCommandLine(args));

    final TestNG testNG = new OSGiTestNG();
    testNG.configure(params);

    try {
      testNG.run();
    } catch (final TestNGException e) {
      e.printStackTrace();
    }
  }
}
