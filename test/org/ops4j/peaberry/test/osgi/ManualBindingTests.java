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

import static org.ops4j.peaberry.Leased.FOREVER;
import static org.ops4j.peaberry.Peaberry.getOSGiServiceRegistry;
import static org.ops4j.peaberry.Peaberry.leased;
import static org.ops4j.peaberry.Peaberry.nonDelegatingContainer;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.Peaberry.serviceProvider;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.ServiceRegistry;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ManualBindingTests", suiteName = "OSGi")
public class ManualBindingTests
    extends OSGiServiceTester {

  @Test(enabled = false)
  public static void setup(Binder binder, BundleContext bundleContext) {

    binder.bind(BundleContext.class).toInstance(bundleContext);

    ServiceRegistry registry = getOSGiServiceRegistry(bundleContext);

    binder.bind(TestService.class).toProvider(
        serviceProvider(registry, TestService.class));

    binder.bind(String.class).toProvider(
        serviceProvider(registry, String.class, null, leased(FOREVER)));

    TypeLiteral<Iterable<TestService>> multiple =
        new TypeLiteral<Iterable<TestService>>() {};

    binder.bind(multiple).toProvider(
        serviceProvider(registry, multiple,
            service("name=B", TestService.class), leased(1)));

    binder.bind(Integer.class).toProvider(
        serviceProvider(registry, TypeLiteral.get(Integer.class)));

    nonDelegatingContainer();
  }

  @Inject
  TestService testService;

  @Inject
  Iterable<TestService> testServices;

  public void testAnnotations() {
    assert service(null).annotationType().equals(Service.class);
    assert leased(3).annotationType().equals(Leased.class);
  }

  public void testUnaryService() {
    disableAllServices();
    enableService("A");
    checkService(testService, "A");
  }

  public void testMultiService() {
    disableAllServices();
    enableService("A");
    enableService("B");
    enableService("C");
    checkServices(testServices, "B");
    disableService("B");
    checkServices(testServices, "!");
    sleep(1100);
    checkServices(testServices);
  }
}
