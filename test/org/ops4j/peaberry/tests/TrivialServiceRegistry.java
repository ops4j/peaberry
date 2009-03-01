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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;

import com.google.inject.Singleton;

/**
 * Trivial example of a custom {@code ServiceRegistry}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Singleton
final class TrivialServiceRegistry
    implements ServiceRegistry {

  final Map<Object, Map<String, ?>> registry = new HashMap<Object, Map<String, ?>>();

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {
    return new Iterable<Import<T>>() {
      public Iterator<Import<T>> iterator() {

        final List<Import<T>> imports = new ArrayList<Import<T>>();

        for (final Entry<Object, Map<String, ?>> e : registry.entrySet()) {

          @SuppressWarnings("unchecked")
          final T service = (T) e.getKey();

          if (clazz.isInstance(service) && (null == filter || filter.matches(e.getValue()))) {
            imports.add(new Import<T>() {

              public T get() {
                return service;
              }

              public Map<String, ?> attributes() {
                return e.getValue();
              }

              public void unget() {}
            });
          }
        }

        return imports.iterator();
      }
    };
  }

  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceScope<? super T> scope) {
    throw new UnsupportedOperationException();
  }

  public <T> Export<T> add(final Import<T> service) {
    registry.put(service.get(), service.attributes());

    return new Export<T>() {

      public T get() {
        return service.get();
      }

      public Map<String, ?> attributes() {
        return service.attributes();
      }

      public void unget() {}

      public void put(final Object newService) {}

      public void attributes(final Map<String, ?> newAttributes) {
        registry.put(service.get(), newAttributes);
      }

      public void unput() {
        registry.remove(service.get());
      }
    };
  }
}
