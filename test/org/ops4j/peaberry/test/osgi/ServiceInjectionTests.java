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

import java.util.Properties;

import org.ops4j.peaberry.Service;
import org.osgi.framework.ServiceRegistration;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ServiceInjectionTests", suiteName = "OSGi")
public class ServiceInjectionTests
    extends OSGiServiceTester {

  @Inject
  @Service
  @Named("field")
  TestService fieldService;

  final TestService ctorService;

  public ServiceInjectionTests() {
    ctorService = null;
  }

  @Inject
  public ServiceInjectionTests(@Service
  @Named("ctor")
  TestService service) {
    ctorService = service;
  }

  TestService setterService;

  @Inject
  protected void setTestService(@Service
  @Named("setter")
  TestService service) {
    setterService = service;
  }

  protected static interface ExtendedTestService
      extends TestService {
    int encode();
  }

  @Inject
  @Service(interfaces = ExtendedTestService.class)
  @Named("extended1")
  Object extendedService1;

  @Inject
  @Service(interfaces = {
      TestService.class, ExtendedTestService.class
  })
  @Named("extended2")
  Object extendedService2;

  @Inject
  @Service(interfaces = {
      TestService.class, ExtendedTestService.class
  })
  @Named("extendedSequence")
  Iterable<?> extendedServices;

  public void checkInjection() {
    disableAllServices();
    missingService(ctorService);
    missingService(fieldService);
    missingService(setterService);
    enableService("A");
    checkService(ctorService, "A");
    checkService(fieldService, "A");
    checkService(setterService, "A");

    enableExtendedService("B");

    assert "B".equals(((TestService) extendedService1).check());
    assert "B".equals(((TestService) extendedService2).check());

    assert extendedService1 instanceof TestService;
    assert extendedService1 instanceof ExtendedTestService;

    assert extendedService2 instanceof TestService;
    assert extendedService2 instanceof ExtendedTestService;

    assert ((ExtendedTestService) extendedService1).encode() == "B".hashCode();
    assert ((ExtendedTestService) extendedService2).encode() == "B".hashCode();

    assert extendedServices.iterator().next() instanceof TestService;
    assert extendedServices.iterator().next() instanceof ExtendedTestService;
  }

  protected void enableExtendedService(final String name) {
    Properties properties = new Properties();
    properties.setProperty("name", name);

    ServiceRegistration registration =
        bundleContext.registerService(new String[] {
            TestService.class.getName(), ExtendedTestService.class.getName()
        }, new ExtendedTestService() {
          public String check() {
            if (registrations.containsKey(name)) {
              return name;
            } else {
              throw new RuntimeException("Missing Service");
            }
          }

          public int encode() {
            if (registrations.containsKey(name)) {
              return name.hashCode();
            } else {
              throw new RuntimeException("Missing Service");
            }
          }
        }, properties);

    registrations.put(name, registration);
  }
}
