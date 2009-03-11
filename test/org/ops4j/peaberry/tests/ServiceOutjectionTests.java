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

import static com.google.inject.name.Names.named;
import static java.util.Collections.singletonMap;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.osgi.framework.Constants.SERVICE_RANKING;
import static org.testng.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.util.AbstractDecorator;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.ops4j.peaberry.util.StaticImport;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Named;

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
  @Named("decorated")
  Id decoratedId;

  @Inject
  @Named("disinterested")
  Id disinterestedId;

  @Inject
  ServiceRegistry registry;

  @Inject
  @Named("single")
  Watcher singleWatcher;

  @Inject
  @Named("multiple")
  Watcher multipleWatcher;

  @Inject
  @Named("decorated")
  Watcher decoratedWatcher;

  static class Decorator
      extends AbstractDecorator<Id> {
    @Override
    protected Id decorate(final Id instance, final Map<String, ?> attributes) {
      return new Id() {
        @Override
        public String toString() {
          return "<=" + instance + "=>";
        }
      };
    }
  }

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

  protected static class DisinterestedWatcher
      extends AbstractWatcher<Id> {

    @Override
    protected Id adding(final Import<Id> service) {
      return null;
    }

    @Override
    protected void modified(Id instance, Map<String, ?> attributes) {
      throw new IllegalStateException();
    }

    @Override
    protected void removed(Id instance) {
      throw new IllegalStateException();
    }

    @Override
    public String toString() {
      return "[]";
    }
  }

  @Override
  protected void configure() {
    // use eager singletons so we get the same instance injected locally
    bind(Watcher.class).annotatedWith(named("single")).to(Watcher.class).asEagerSingleton();
    bind(Watcher.class).annotatedWith(named("multiple")).to(Watcher.class).asEagerSingleton();
    bind(Watcher.class).annotatedWith(named("decorated")).to(Watcher.class).asEagerSingleton();

    bind(Id.class).toProvider(
        service(Id.class).out(Key.get(Watcher.class, named("single"))).single());

    bind(iterable(Id.class)).toProvider(service(Id.class).filter(new AttributeFilter() {
      public boolean matches(Map<String, ?> attributes) {
        return null == attributes || !attributes.containsKey("IGNORE_ME");
      }
    }).out(Key.get(Watcher.class, named("multiple"))).multiple());

    bind(Id.class).annotatedWith(named("decorated")).toProvider(
        service(Id.class).decoratedWith(new Decorator()).out(
            Key.get(Watcher.class, named("decorated"))).single());

    bind(Id.class).annotatedWith(named("disinterested")).toProvider(
        service(Id.class).decoratedWith(new Decorator()).out(new DisinterestedWatcher()).single());
  }

  public void testServiceOutjection() {
    reset();

    register("A");

    check(singleWatcher, "[A]");
    check(multipleWatcher, "[A]");
    check(decoratedWatcher, "[<=A=>]");

    register("B");

    check(singleWatcher, "[A]");
    check(multipleWatcher, "[A, B]");
    check(decoratedWatcher, "[<=A=>]");

    final Export<Id> exportedId = registry.add(new StaticImport<Id>(new Id() {
      @Override
      public String toString() {
        return "C";
      }
    }));

    check(singleWatcher, "[A]");
    check(multipleWatcher, "[A, B, C]");
    check(decoratedWatcher, "[<=A=>]");

    register(8, "D");

    check(singleWatcher, "[D]");
    check(multipleWatcher, "[A, B, C, D]");
    check(decoratedWatcher, "[<=D=>]");

    exportedId.attributes(singletonMap(SERVICE_RANKING, 42));

    check(singleWatcher, "[C]");
    check(multipleWatcher, "[A, B, C, D]"); // order of appearance, not ranking
    check(decoratedWatcher, "[<=C=>]");

    exportedId.put(new Id() {
      @Override
      public String toString() {
        return "E";
      }
    });

    check(singleWatcher, "[E]");
    check(multipleWatcher, "[A, B, D, E]");
    check(decoratedWatcher, "[<=E=>]");

    unregister("A");

    check(singleWatcher, "[E]");
    check(multipleWatcher, "[B, D, E]");
    check(decoratedWatcher, "[<=E=>]");

    exportedId.attributes(singletonMap("IGNORE_ME", null));

    check(singleWatcher, "[D]");
    check(multipleWatcher, "[B, D]");
    check(decoratedWatcher, "[<=D=>]");

    exportedId.attributes(null);

    check(singleWatcher, "[D]");
    check(multipleWatcher, "[B, D, E]");
    check(decoratedWatcher, "[<=D=>]");

    exportedId.unput();

    check(singleWatcher, "[D]");
    check(multipleWatcher, "[B, D]");
    check(decoratedWatcher, "[<=D=>]");

    unregister("D");

    check(singleWatcher, "[B]");
    check(multipleWatcher, "[B]");
    check(decoratedWatcher, "[<=B=>]");
  }

  public void testServiceWatcher() {
    final ServiceWatcher<String> watcher = new AbstractWatcher<String>() {};
    final Export<String> export =
        watcher.add(new StaticImport<String>("hello", singletonMap("KEY", "VALUE")));

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
