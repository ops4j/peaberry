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

import java.io.Serializable;

import org.ops4j.peaberry.Service;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Named;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ServiceFilterTests
    extends AbstractServiceTests
    implements Module {

  public void configure(Binder binder) {
    binder.bind(ServiceFilterTests.class);
  }

  @Inject
  @Service(value = "name=Z", interfaces = {
      TestService.class, Serializable.class
  })
  @Named("api")
  TestService customAPIService;

  @Inject
  @Service(value = "&(name=Z)(objectclass=org.ops4j.peaberry.test.AbstractServiceTests$TestService)", interfaces = {
      TestService.class, Serializable.class
  })
  @Named("filter")
  TestService filteredService;

  protected interface GenericService<T> {
    T check();
  }

  @Inject
  @Service
  @Named("generic")
  Iterable<GenericService<String>> genericService;

  @Test
  public void checkFilters() {
    disableAllServices();
    missingService(customAPIService);
    missingService(filteredService);
    enableService("A");
    enableService("Z");
    enableService("B");
    missingService(customAPIService);
    checkService(filteredService, "Z");
  }
}
