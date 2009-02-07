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

package org.ops4j.peaberry.osgi;

import java.util.Iterator;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;

import com.google.inject.Inject;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class RegistryChain
    implements ServiceRegistry {

  final ServiceRegistry[] registries;

  @Inject
  public RegistryChain(final CachingServiceRegistry mainRegistry,
      final ServiceRegistry[] alternativeRegistries) {

    registries = new ServiceRegistry[1 + alternativeRegistries.length];
    System.arraycopy(alternativeRegistries, 0, registries, 1, alternativeRegistries.length);
    registries[0] = mainRegistry;
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {

    @SuppressWarnings("unchecked")
    final Iterable<Import<T>>[] iterables = new Iterable[registries.length];
    for (int i = 0; i < iterables.length; i++) {
      iterables[i] = registries[i].lookup(clazz, filter);
    }

    return new Iterable<Import<T>>() {
      public Iterator<Import<T>> iterator() {
        return new IteratorChain<T>(iterables);
      }
    };
  }

  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceScope<? super T> scope) {
    for (final ServiceRegistry r : registries) {
      r.watch(clazz, filter, scope);
    }
  }

  public <T> Export<T> add(final Import<T> service) {
    for (final ServiceRegistry r : registries) {
      final Export<T> export = r.add(service);
      if (export != null) {
        return export;
      }
    }
    return null;
  }
}
