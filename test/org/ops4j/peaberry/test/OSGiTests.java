/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry.test;

import junit.framework.TestSuite;

import org.osgi.framework.BundleContext;

public class OSGiTests {

  static BundleContext m_bundleContext;

  public static BundleContext getBundleContext() {
    return m_bundleContext;
  }

  public static TestSuite suite(BundleContext bc) {
    TestSuite suite = new TestSuite();
    m_bundleContext = bc;

    suite.addTestSuite(OSGiServiceBindingTest.class);

    return suite;
  }
}
