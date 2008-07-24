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

package org.ops4j.peaberry.test.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;

/**
 * Test service ranking.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(testName = "ServiceRankingTests", suiteName = "OSGi")
public final class ServiceRankingTests
    extends OSGiServiceTester {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    binder.bind(iterable(SimpleService.class)).toProvider(service(SimpleService.class).multiple());
  }

  @Inject
  Iterable<SimpleService> services;

  public void checkRanking() {
    disableAllServices();

    enableService("F", -2);
    enableService("B", 8);
    enableService("A", 42);
    enableService("E", 0);
    enableService("G", -2);
    enableService("C", 8);
    enableService("D", 1);

    checkServices(services, "A", "B", "C", "D", "E", "F", "G");

    try {
      services.iterator().remove();
      assert false : "Expected UnsupportedOperationException";
    } catch (final UnsupportedOperationException e) {}

    disableAllServices();
  }
}
