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

package org.ops4j.peaberry.test.internal;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.internal.ServiceFilterFactory.getServiceFilter;

import org.ops4j.peaberry.Service;
import org.testng.annotations.Test;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ServiceFilterTests", suiteName = "Internal")
public final class ServiceFilterTests {

  private interface A {}

  private interface B {}

  private interface C {}

  private void checkFilter(String filter, Service spec) {
    String result = getServiceFilter(spec, A.class);
    assert filter.equals(result) : "Expected " + filter + ", got " + result;
  }

  private String objectclass(String name) {
    return "objectclass=" + getClass().getName() + "$" + name;
  }

  public void serviceFilters() {
    checkFilter("(" + objectclass("A") + ")", null);

    checkFilter("(&(" + objectclass("C") + ")(" + objectclass("A") + ")("
        + objectclass("B") + "))", service(null, C.class, A.class, B.class));

    checkFilter("(&(|(name=THIS)(name=THAT))(" + objectclass("A") + "))",
        service("|(name=THIS)(name=THAT)"));

    checkFilter("(OBJECTCLASS=TEST)", service("OBJECTCLASS=TEST"));
  }
}
