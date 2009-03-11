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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.util.SimpleExport;

import com.google.inject.Singleton;

/**
 * Trivial (not fully implemented) example of a custom {@code ServiceRegistry}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Singleton
final class TrivialServiceRegistry
    implements ServiceRegistry {

  final List<Import<?>> registry = new ArrayList<Import<?>>();

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {
    return new Iterable<Import<T>>() {

      @SuppressWarnings("unchecked")
      public Iterator<Import<T>> iterator() {
        final List<Import<T>> imports = new ArrayList<Import<T>>();

        for (Import<?> i : registry) {
          if (clazz.isInstance(i.get()) && (null == filter || filter.matches(i.attributes()))) {
            imports.add((Import) i);
          }
        }

        return imports.iterator();
      }
    };
  }

  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceWatcher<? super T> watcher) {
    throw new UnsupportedOperationException();
  }

  public <T> Export<T> add(final Import<T> service) {
    registry.add(service);

    return new SimpleExport<T>(service) {

      @Override
      public void put(final Object newService) {
        throw new UnsupportedOperationException();
      }

      @Override
      public void attributes(final Map<String, ?> newAttributes) {
        throw new UnsupportedOperationException();
      }

      @Override
      public void unput() {
        registry.remove(service);
      }
    };
  }
}
