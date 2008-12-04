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

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.testng.Assert.assertTrue;

import java.lang.annotation.Retention;

import org.ops4j.peaberry.Export;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;

import examples.ids.Id;

/**
 * Test direct service injection.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class DirectServiceTests
    extends InjectableTestCase {

  @Retention(RUNTIME)
  @interface Nullable {}

  // used for lazy binding
  static class Holder {
    @Inject
    @Nullable
    Id id;

    @Inject
    Iterable<Id> ids;
  }

  static class DirectIdImpl
      implements Id {

    @Override
    public String toString() {
      return "DIRECT";
    }
  }

  @Override
  protected void configure() {
    bind(Id.class).toProvider(service(Id.class).single().direct());
    bind(iterable(Id.class)).toProvider(service(Id.class).multiple().direct());

    bind(export(Id.class)).toProvider(service(Key.get(DirectIdImpl.class)).export());
  }

  public void testDirectServiceInjection() {
    reset();

    final Export<? extends Id> exportedId = getInstance(Key.get(export(Id.class)));

    // direct binding to existing service
    final Holder hasService = getInstance(Key.get(Holder.class));

    check(hasService.id, "DIRECT");
    check(hasService.ids, "[DIRECT]");

    assertTrue(hasService.id instanceof DirectIdImpl);
    assertTrue(hasService.ids.iterator().next() instanceof DirectIdImpl);

    exportedId.unput();

    // direct binding to non-existent service
    final Holder noService = getInstance(Key.get(Holder.class));

    assertTrue(null == noService.id);
    check(noService.ids, "[]");

    // Note: we can still call the direct service here, because its binding is
    // fixed - but in real life it would probably start throwing exceptions...

    check(hasService.id, "DIRECT");
    check(hasService.ids, "[DIRECT]");
  }
}
