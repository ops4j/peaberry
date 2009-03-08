/**
 * Copyright (C) 2009 Stuart McCulloch
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

import static java.util.Collections.singletonMap;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.osgi.framework.Constants.SERVICE_RANKING;
import static org.testng.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import examples.ids.Id;

/**
 * Test service outjection (AKA watching for services).
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceOutjectionTests
    extends InjectableTestCase {

  @Inject
  Id id;

  @Inject
  Id anotherId;

  @Inject
  Iterable<Id> ids;

  @Inject
  ServiceRegistry registry;

  @Inject
  @Named("single")
  Watcher singleWatcher;

  @Inject
  @Named("multiple")
  Watcher multipleWatcher;

  protected static class Watcher
      extends AbstractWatcher<Id> {

    final Set<Id> watchedIds = new LinkedHashSet<Id>();

    @Override
    protected Id adding(final Import<Id> service) {
      final Id instance = super.adding(service);
      watchedIds.add(instance);
      return instance;
    }

    @Override
    protected void removed(final Id instance) {
      watchedIds.remove(instance);
      super.removed(instance);
    }

    @Override
    public String toString() {
      return watchedIds.toString();
    }
  }

  @Override
  protected void configure() {
    bind(Watcher.class).annotatedWith(Names.named("single")).to(Watcher.class).asEagerSingleton();
    bind(Watcher.class).annotatedWith(Names.named("multiple")).to(Watcher.class).asEagerSingleton();

    bind(Id.class).toProvider(
        service(Id.class).out(Key.get(Watcher.class, Names.named("single"))).single());

    bind(iterable(Id.class)).toProvider(
        service(Id.class).out(Key.get(Watcher.class, Names.named("multiple"))).multiple());
  }

  public void testServiceOutjection() {
    reset();

    register("A");

    check(singleWatcher, "[A]");
    check(multipleWatcher, "[A]");

    register("B");

    check(singleWatcher, "[A]");
    check(multipleWatcher, "[A, B]");

    final Export<Id> exportedId = registry.add(new Import<Id>() {
      public Id get() {
        return new Id() {
          @Override
          public String toString() {
            return "C";
          }
        };
      }

      public Map<String, ?> attributes() {
        return null;
      }

      public void unget() {}
    });

    check(singleWatcher, "[A]");
    check(multipleWatcher, "[A, B, C]");

    register(8, "D");

    check(singleWatcher, "[D]");
    check(multipleWatcher, "[A, B, C, D]");

    exportedId.attributes(singletonMap(SERVICE_RANKING, 42));

    check(singleWatcher, "[C]");
    check(multipleWatcher, "[A, B, C, D]");

    exportedId.put(new Id() {
      @Override
      public String toString() {
        return "E";
      }
    });

    check(singleWatcher, "[E]");
    check(multipleWatcher, "[A, B, D, E]");

    unregister("A");

    check(singleWatcher, "[E]");
    check(multipleWatcher, "[B, D, E]");

    exportedId.unput();

    check(singleWatcher, "[D]");
    check(multipleWatcher, "[B, D]");

    unregister("D");

    check(singleWatcher, "[B]");
    check(multipleWatcher, "[B]");
  }

  public void testServiceWatcher() {
    final ServiceWatcher<String> watcher = new AbstractWatcher<String>() {};
    final Export<String> export = watcher.add(new Import<String>() {

      public String get() {
        return "hello";
      }

      public Map<String, ?> attributes() {
        return singletonMap("KEY", "VALUE");
      }

      public void unget() {}
    });

    assertEquals(export.get(), "hello");
    assertEquals(export.attributes().get("KEY"), "VALUE");
    export.unget();

    export.unput();

    assertEquals(export.get(), null);
    assertEquals(export.attributes().get("KEY"), "VALUE");
    export.unget();

    export.attributes(singletonMap("KEY", null));

    assertEquals(export.get(), null);
    assertEquals(export.attributes().get("KEY"), null);
    export.unget();

    export.put("world");

    assertEquals(export.get(), "world");
    assertEquals(export.attributes().get("KEY"), null);
    export.unget();

    export.put(null);

    assertEquals(export.get(), null);
    assertEquals(export.attributes().get("KEY"), null);
    export.unget();
  }
}
