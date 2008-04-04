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

import org.ops4j.peaberry.Service;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Named;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ServiceInjectionTests
    extends AbstractServiceTests
    implements Module {

  public void configure(Binder binder) {
    binder.bind(ServiceInjectionTests.class);
  }

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
  public void setTestService(@Service
  @Named("setter")
  TestService service) {
    setterService = service;
  }

  @Test
  public void checkInjection() {
    disableAllServices();
    missingService(ctorService);
    missingService(fieldService);
    missingService(setterService);
    enableService("A");
    checkService(ctorService, "A");
    checkService(fieldService, "A");
    checkService(setterService, "A");
  }
}
