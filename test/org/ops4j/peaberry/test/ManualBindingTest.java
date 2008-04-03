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

import static org.ops4j.peaberry.Peaberry.getOSGiServiceRegistry;
import static org.ops4j.peaberry.Peaberry.nonDelegatingContainer;
import static org.ops4j.peaberry.Peaberry.serviceProvider;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.ServiceRegistry;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ManualBindingTest
    extends AbstractServiceTest
    implements Module {

  public void configure(Binder binder) {

    BundleContext bundleContext = TestActivator.getBundleContext();
    ServiceRegistry registry = getOSGiServiceRegistry(bundleContext);
    binder.bind(BundleContext.class).toInstance(bundleContext);

    TypeLiteral<TestService> unary = new TypeLiteral<TestService>() {};
    binder.bind(unary).toProvider(serviceProvider(registry, unary));

    TypeLiteral<Iterable<TestService>> multi =
        new TypeLiteral<Iterable<TestService>>() {};

    Service spec = Peaberry.service("name=B", TestService.class);
    Leased leased = Peaberry.leased(1);

    binder.bind(multi).toProvider(
        serviceProvider(registry, multi, spec, leased));

    nonDelegatingContainer();
  }

  @Inject
  TestService testService;

  @Inject
  Iterable<TestService> testServices;

  @Test
  public void testUnaryService() {
    disableAllServices();
    enableService("A");
    checkService(testService, "A");
  }

  @Test
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
