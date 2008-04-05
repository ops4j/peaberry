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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.main.AutoActivator;
import org.osgi.framework.BundleContext;
import org.testng.ITestRunnerFactory;
import org.testng.TestNG;
import org.testng.TestNGException;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiTestNG
    extends TestNG {

  @Override
  @SuppressWarnings("unchecked")
  public void run() {

    System.out.println("=====================");
    System.out.println("Start Felix container");
    System.out.println("=====================");

    Map config = new StringMap(loadConfigProperties(), false);

    List autoActivatorList = new ArrayList();
    autoActivatorList.add(new AutoActivator(config));

    Felix felix = new Felix(config, autoActivatorList);

    try {
      felix.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {

      Class clazz = felix.loadClass(System.getProperty("runnerFactoryClass"));
      Constructor ctor = clazz.getConstructor(BundleContext.class);
      Object factory = ctor.newInstance(felix.getBundleContext());
      setTestRunnerFactory((ITestRunnerFactory) factory);

      super.run();

    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        felix.stop();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Map params = checkConditions(parseCommandLine(args));

    TestNG testNG = new OSGiTestNG();
    testNG.configure(params);

    try {
      testNG.run();
    } catch (TestNGException e) {
      e.printStackTrace();
    }
  }
}
