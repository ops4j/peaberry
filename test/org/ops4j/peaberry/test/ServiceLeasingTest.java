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

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Service;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Named;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ServiceLeasingTest
    extends AbstractServiceTest
    implements Module {

  public void configure(Binder binder) {
    binder.bind(ServiceLeasingTest.class);
  }

  @Inject
  @Service
  @Named("unleased")
  TestService unleasedService;

  @Inject
  @Leased(seconds = 2)
  @Service
  @Named("leased")
  TestService leasedService;

  @Inject
  @Leased(seconds = 2)
  @Service
  @Named("leased")
  Iterable<TestService> leasedServices;

  @Inject
  @Leased(seconds = Leased.FOREVER)
  @Service
  @Named("static")
  TestService staticService;

  @Inject
  @Leased(seconds = Leased.FOREVER)
  @Service
  @Named("static")
  Iterable<TestService> staticServices;

  @Test
  public void unleasedUnaryService() {
    disableAllServices();
    missingService(unleasedService);
    enableService("A");
    checkService(unleasedService, "A");
    enableService("B");
    checkService(unleasedService, "B");
    disableService("B");
    checkService(unleasedService, "A");
    disableService("A");
    missingService(unleasedService);
    enableService("A");
    checkService(unleasedService, "A");
    enableService("B");
    checkService(unleasedService, "B");
    disableService("A");
    checkService(unleasedService, "B");
    disableService("B");
    missingService(unleasedService);
  }

  @Test
  public void leasedUnaryService() {
    disableAllServices();
    missingService(leasedService);
    enableService("A");
    checkService(leasedService, "A");
    enableService("B");
    checkService(leasedService, "A");
    sleep(2200);
    checkService(leasedService, "B");
    disableService("B");
    missingService(leasedService);
    sleep(2200);
    checkService(leasedService, "A");
    disableService("A");
    missingService(leasedService);
  }

  @Test
  public void leasedMultiService() {
    disableAllServices();
    checkServices(leasedServices);
    enableService("A");
    checkServices(leasedServices, "A");
    enableService("B");
    checkServices(leasedServices, "A");
    sleep(2200);
    checkServices(leasedServices, "B", "A");
    disableService("B");
    checkServices(leasedServices, "!", "A");
    sleep(2200);
    checkServices(leasedServices, "A");
    disableService("A");
    checkServices(leasedServices, "!");
    sleep(2200);
    checkServices(leasedServices);
  }

  @Test
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
  }

  @Test
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
  }
}
