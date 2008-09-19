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

package org.ops4j.peaberry.test.cases;

import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.*;
import static org.ops4j.peaberry.util.Filters.ldap;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import org.ops4j.peaberry.Export;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;

import examples.ids.IdService;

/**
 * Test exporting of service implementations to the {@code ServiceRegistry}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceExportTests
    extends InjectableTestCase {

  @Inject
  Export<IdService> exportedId;

  @Inject
  IdService importedId;

  private static class IdServiceImpl
      implements IdService {

    @Override
    public String toString() {
      return "OK";
    }
  }

  @Override
  protected void configure() {
    bind(IdService.class).toProvider(service(IdService.class).filter(ldap("(id=TEST)")).single());
    bind(export(IdService.class)).toProvider(registration(Key.get(IdServiceImpl.class)).export());
  }

  public void testServiceExports() {
    reset();

    missing(importedId);
    exportedId.modify(names("id=TEST"));
    check(importedId, "OK");
    exportedId.remove();
    missing(importedId);
  }
}
