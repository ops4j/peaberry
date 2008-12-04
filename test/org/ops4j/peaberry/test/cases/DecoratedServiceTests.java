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

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import java.util.Map;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.QualifiedServiceBuilder;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.testng.annotations.Test;

import com.google.inject.Inject;
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

  static class IdAdapter
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

  static class IdDecorator
      implements ImportDecorator<Id> {

    public <T extends Id> Import<T> decorate(final Import<T> handle) {
      return new Import<T>() {

        @SuppressWarnings("unchecked")
        public T get() {
          return (T) new IdAdapter(handle.get());
        }

        public Map<String, ?> attributes() {
          return handle.attributes();
        }

        public void unget() {
          handle.unget();
        }
      };
    }
  }

  @Inject
  Iterable<Id> ids;

  @Override
  protected void configure() {
    final QualifiedServiceBuilder<Id> builder =
        service(Id.class).decoratedWith(Key.get(IdDecorator.class));

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
}
