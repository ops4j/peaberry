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

import org.osgi.framework.BundleContext;
import org.testng.ISuite;
import org.testng.ITestRunnerFactory;
import org.testng.TestRunner;
import org.testng.reporters.TestHTMLReporter;
import org.testng.xml.XmlTest;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiRunnerFactory
    implements ITestRunnerFactory {

  final BundleContext bundleContext;

  public OSGiRunnerFactory(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  public TestRunner newTestRunner(ISuite suite, XmlTest test) {

    TestRunner runner;
    if (suite.getName().startsWith("OSGi")) {
      runner = new TestRunner(suite, test, false);
    } else {
      runner = new TestRunner(suite, test, false);
    }

    runner.addListener(new TestHTMLReporter());

    return runner;
  }
}
