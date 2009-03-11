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

package org.ops4j.peaberry.tests;

import static com.google.inject.name.Names.named;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.lang.annotation.Retention;
import java.util.Arrays;
import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.util.StaticImport;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;

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
  public static class Holder {
    @Inject
    @Nullable
    Id id;

    @Inject
    Iterable<Id> ids;
  }

  public static class DirectIdImpl
      implements Id {

    @Override
    public String toString() {
      return "DIRECT";
    }
  }

  @Inject
  @Named("in")
  CharSequence inToken;

  @Inject
  @Named("out")
  CharSequence outToken;

  public DirectServiceTests() {
    super(new ServiceRegistry() {
      private final Import<?>[] bogusImports =
          new Import[]{new StaticImport<Object>(null), new Import<Object>() {
            public Object get() {
              throw new ServiceUnavailableException();
            }

            public Map<String, ?> attributes() {
              return null;
            }

            public void unget() {}

            public boolean available() {
              return false;
            }
          }};

      @SuppressWarnings("unchecked")
      public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {
        return Arrays.asList((Import<T>[]) bogusImports);
      }

      public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
          final ServiceWatcher<? super T> watcher) {
        throw new UnsupportedOperationException();
      }

      public <T> Export<T> add(final Import<T> service) {
        throw new UnsupportedOperationException();
      }
    });
  }

  @Override
  protected void configure() {
    bind(Id.class).toProvider(service(Id.class).single().direct());
    bind(iterable(Id.class)).toProvider(service(Id.class).multiple().direct());
    bind(export(Id.class)).toProvider(service(TypeLiteral.get(DirectIdImpl.class)).export());

    bind(CharSequence.class).annotatedWith(named("out")).toProvider(
        service("TOKEN").export().direct());
    bind(CharSequence.class).annotatedWith(named("in")).toProvider(
        service(CharSequence.class).single());
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

    assertNull(noService.id);
    check(noService.ids, "[]");

    // Note: we can still call the direct service here, because its binding is
    // fixed - but in real life it would probably start throwing exceptions...

    check(hasService.id, "DIRECT");
    check(hasService.ids, "[DIRECT]");

    assertEquals(inToken.toString(), outToken.toString());
  }
}
