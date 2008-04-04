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

import static org.testng.TestNGCommandLineArgs.parseCommandLine;

import java.util.Map;

import org.testng.ISuite;
import org.testng.ITestRunnerFactory;
import org.testng.TestNG;
import org.testng.TestNGException;
import org.testng.TestRunner;
import org.testng.xml.XmlTest;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiTestNG
    extends TestNG {

  public OSGiTestNG() {
    super();

    System.out.println("=====================");
    System.out.println("peaberry test harness");
    System.out.println("=====================");

    setTestRunnerFactory(new ITestRunnerFactory() {
      public TestRunner newTestRunner(ISuite suite, XmlTest test) {
        if (suite.getName().startsWith("OSGi")) {
          return new OSGiTestRunner(suite, test);
        } else {
          return new TestRunner(suite, test, false);
        }
      }
    });
  }

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Map params = checkConditions(parseCommandLine(args));

    TestNG testNG = new OSGiTestNG();
    testNG.configure(params);

    testNG.setObjectFactory(GuiceObjectFactory.class);

    try {
      testNG.run();
    } catch (TestNGException e) {
      e.printStackTrace();
    }
  }
}
