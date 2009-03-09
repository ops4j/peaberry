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
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.osgi.framework.Constants.SERVICE_DESCRIPTION;
import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.builders.ServiceBuilder;
import org.ops4j.peaberry.util.AbstractDecorator;
import org.ops4j.peaberry.util.Decorators;
import org.ops4j.peaberry.util.DelegatingImport;
import org.ops4j.peaberry.util.StaticImport;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

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
      return new DelegatingImport<T>(service) {

        @Override
        public Map<String, ?> attributes() {
          return union(super.attributes(), singletonMap(SERVICE_DESCRIPTION, "DECORATED"));
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
  }

  public void testDecoratedServiceInjection() {
    reset();

    register("A", "B", "C");

    // lazy load of direct service instance
    check(getInstance(Key.get(Id.class)), "<-A->");

    check(ids, "[<-A->, <-B->, <-C->]");
  }

  public void testDecoratorChaining() {
    final String text = Decorators.chain(new AbstractDecorator<String>() {
      @Override
      protected String decorate(final String instance, final Map<String, ?> attributes) {
        return "<1>" + instance + "<4>";
      }
    }, new AbstractDecorator<String>() {
      @Override
      protected String decorate(final String instance, final Map<String, ?> attributes) {
        return "<2>" + instance + "<3>";
      }
    }).decorate(new StaticImport<String>("HELLO")).get();

    assertEquals(text, "<1><2>HELLO<3><4>");
  }
}
