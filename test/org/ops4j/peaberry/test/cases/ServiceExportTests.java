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
import static org.ops4j.peaberry.util.Attributes.names;
import static org.ops4j.peaberry.util.Filters.ldap;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import org.ops4j.peaberry.Export;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;

import examples.ids.Id;

/**
 * Test exporting of service implementations to the {@code ServiceRegistry}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceExportTests
    extends InjectableTestCase {

  @Inject
  Export<Id> exportedId;

  @Inject
  Id importedId;

  private static class IdImpl
      implements Id {

    @Override
    public String toString() {
      return "OK";
    }
  }

  @Override
  protected void configure() {
    bind(Id.class).toProvider(service(Id.class).filter(ldap("(id=TEST)")).single());
    bind(export(Id.class)).toProvider(registration(Key.get(IdImpl.class)).export());
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
