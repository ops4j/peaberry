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

import static com.google.inject.name.Names.named;
import static org.ops4j.peaberry.Peaberry.nonDelegatingContainer;
import static org.ops4j.peaberry.Peaberry.osgiServiceRegistry;
import static org.ops4j.peaberry.Peaberry.serviceProvider;
import static org.ops4j.peaberry.util.ServiceBuilder.service;

import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.ServiceRegistry;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;

/**
 * Test manual configuration of OSGi service injection.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ManualBindingTests", suiteName = "OSGi")
public class ManualBindingTests
    extends OSGiServiceTester {

  @SuppressWarnings("serial")
  @Test(enabled = false)
  public static void setup(final Binder binder, final BundleContext bundleContext) {
    nonDelegatingContainer();

    final ServiceRegistry registry = osgiServiceRegistry(bundleContext);

    binder.bind(BundleContext.class).toInstance(bundleContext);
    binder.bind(ServiceRegistry.class).toInstance(registry);

    final Service specificService = service().filter("name=B").lease(1).build();

    binder.bind(SimpleService.class).toProvider(serviceProvider(registry, SimpleService.class));

    binder.bind(SimpleService.class).annotatedWith(named("specific")).toProvider(
        serviceProvider(registry, SimpleService.class, specificService));

    final TypeLiteral<Iterable<SimpleService>> multiple = new TypeLiteral<Iterable<SimpleService>>() {};

    binder.bind(multiple).toProvider(serviceProvider(registry, multiple));

    binder.bind(multiple).annotatedWith(named("specific")).toProvider(
        serviceProvider(registry, multiple, specificService));
  }

  @Inject
  SimpleService testService;

  @Inject
  @Named("specific")
  SimpleService specificService;

  @Inject
  Iterable<SimpleService> testServices;

  @Inject
  @Named("specific")
  Iterable<SimpleService> specificServices;

  public void testUnaryService() {
    disableAllServices();
    enableService("A");
    enableService("B");
    enableService("C");
    checkService(testService, "A");
    checkService(specificService, "B");
  }

  public void testMultiService() {
    disableAllServices();
    enableService("A");
    enableService("B");
    enableService("C");
    checkServices(testServices, "A", "B", "C");
    checkServices(specificServices, "B");
    disableService("B");
    checkServices(testServices, "A", "C");
    checkServices(specificServices, "!");
    sleep(1100);
    checkServices(specificServices);
  }
}
