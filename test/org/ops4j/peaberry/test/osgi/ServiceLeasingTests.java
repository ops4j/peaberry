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
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * Test leasing of services, both timed and static.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ServiceLeasingTests", suiteName = "OSGi")
public class ServiceLeasingTests
    extends OSGiServiceTester {

  private static class LeasedDecorator
      implements ImportDecorator<Object> {

    public <T> Import<T> decorate(final Import<T> handle) {
      return handle;// TODO: leasing algorithm
    }
  }

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    final Key<LeasedDecorator> leasedDecoratorKey = Key.get(LeasedDecorator.class);

    binder.bind(SimpleService.class).toProvider(service(SimpleService.class).single());

    binder.bind(SimpleService.class).annotatedWith(named("leased")).toProvider(
        service(SimpleService.class).decoratedWith(leasedDecoratorKey).single());

    binder.bind(SimpleService.class).annotatedWith(named("static")).toProvider(
        service(SimpleService.class).constant().single());

    binder.bind(iterable(SimpleService.class)).annotatedWith(named("leased")).toProvider(
        service(SimpleService.class).decoratedWith(leasedDecoratorKey).multiple());

    binder.bind(iterable(SimpleService.class)).annotatedWith(named("static")).toProvider(
        service(SimpleService.class).constant().multiple());
  }

  @Inject
  SimpleService unleasedService;

  @Inject
  @Named("leased")
  SimpleService leasedService;

  @Inject
  @Named("leased")
  Iterable<SimpleService> leasedServices;

  @Inject
  @Named("static")
  SimpleService staticService;

  @Inject
  @Named("static")
  Iterable<SimpleService> staticServices;

  public void unleasedUnaryService() {
    disableAllServices();
    missingService(unleasedService);
    enableService("A");
    checkService(unleasedService, "A");
    enableService("B");
    checkService(unleasedService, "A");
    disableService("A");
    checkService(unleasedService, "B");
    disableService("B");
    missingService(unleasedService);
    disableAllServices();
  }

  public void leasedUnaryService() {
    disableAllServices();
    missingService(leasedService);
    enableService("A");
    checkService(leasedService, "A");
    disableService("A");
    enableService("B");
    missingService(leasedService);
    sleep(2200);
    checkService(leasedService, "B");
    disableService("B");
    missingService(leasedService);
    disableAllServices();
  }

  public void leasedMultiService() {
    disableAllServices();
    checkServices(leasedServices);
    enableService("A");
    checkServices(leasedServices, "A");
    enableService("B");
    checkServices(leasedServices, "A");
    sleep(2200);
    checkServices(leasedServices, "A", "B");
    disableService("A");
    checkServices(leasedServices, "!", "B");
    sleep(2200);
    checkServices(leasedServices, "B");
    disableService("B");
    checkServices(leasedServices, "!");
    sleep(2200);
    checkServices(leasedServices);
    disableAllServices();
  }

  public void staticUnaryService() {
    disableAllServices();
    missingService(staticService);
    enableService("A");
    checkService(staticService, "A");
    enableService("B");
    checkService(staticService, "A");
    sleep(2200);
    checkService(staticService, "A");
    disableService("A");
    missingService(staticService);
    sleep(2200);
    missingService(staticService);
    disableAllServices();
  }

  public void staticMultiService() {
    disableAllServices();
    checkServices(staticServices);
    enableService("A");
    checkServices(staticServices, "A");
    enableService("B");
    checkServices(staticServices, "A");
    sleep(2200);
    checkServices(staticServices, "A");
    disableService("A");
    checkServices(staticServices, "!");
    sleep(2200);
    checkServices(staticServices, "!");
    disableAllServices();
  }
}
