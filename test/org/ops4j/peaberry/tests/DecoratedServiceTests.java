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

import static java.util.Collections.singletonMap;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.union;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.osgi.framework.Constants.SERVICE_DESCRIPTION;
import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.builders.ServiceBuilder;
import org.ops4j.peaberry.util.AbstractDecorator;
import org.ops4j.peaberry.util.Decorators;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import examples.ids.Id;

/**
 * Test decorated service injection.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class DecoratedServiceTests
    extends InjectableTestCase {

  public static class IdAdapter
      implements Id {

    final Id wrappedId;

    public IdAdapter(final Id wrappedId) {
      this.wrappedId = wrappedId;
    }

    @Override
    public String toString() {
      return "<-" + wrappedId.toString() + "->";
    }
  }

  public static class IdDecorator
      extends AbstractDecorator<Id> {

    @Override
    public Id decorate(final Id instance, final Map<String, ?> attributes) {
      return new IdAdapter(instance);
    }
  }

  public static class AttributeDecorator
      implements ImportDecorator<Id> {

    public <T extends Id> Import<T> decorate(final Import<T> service) {
      return new Import<T>() {

        public T get() {
          return service.get();
        }

        public Map<String, ?> attributes() {
          return union(service.attributes(), singletonMap(SERVICE_DESCRIPTION, "DECORATED"));
        }

        public void unget() {
          service.unget();
        }
      };
    }
  }

  @Inject
  Iterable<Id> ids;

  @Inject
  Injector injector;

  @Override
  protected void configure() {
    final ServiceBuilder<Id> builder = service(Id.class).decoratedWith(Key.get(IdDecorator.class));

    bind(iterable(Id.class)).toProvider(builder.multiple());
    bind(Id.class).toProvider(builder.single().direct());

    bind(export(Id.class)).annotatedWith(Names.named("id")).toProvider(
        service(new Id() {}).decoratedWith(new IdDecorator()).export());

    bind(export(Id.class)).annotatedWith(Names.named("attributes")).toProvider(
        service(new Id() {}).decoratedWith(new AttributeDecorator()).export());
  }

  public void testDecoratedServiceInjection() {
    reset();

    register("A", "B", "C");

    // lazy load of direct service instance
    check(getInstance(Key.get(Id.class)), "<-A->");

    check(ids, "[<-A->, <-B->, <-C->]");

    Export<? extends Id> exportedId;

    exportedId = injector.getInstance(Key.get(export(Id.class), Names.named("id")));
    assertEquals(exportedId.attributes().get(SERVICE_DESCRIPTION), null);
    exportedId.unput();

    exportedId = injector.getInstance(Key.get(export(Id.class), Names.named("attributes")));
    assertEquals(exportedId.attributes().get(SERVICE_DESCRIPTION), "DECORATED");
    exportedId.unput();
  }

  public void testDecoratorChaining() {
    final String text = Decorators.chain(new AbstractDecorator<String>() {
      @Override
      protected String decorate(String instance, Map<String, ?> attributes) {
        return "<1>" + instance + "<4>";
      }
    }, new AbstractDecorator<String>() {
      @Override
      protected String decorate(String instance, Map<String, ?> attributes) {
        return "<2>" + instance + "<3>";
      }
    }).decorate(new Import<String>() {

      public String get() {
        return "HELLO";
      }

      public Map<String, ?> attributes() {
        return null;
      }

      public void unget() {}
    }).get();

    assertEquals(text, "<1><2>HELLO<3><4>");
  }
}
