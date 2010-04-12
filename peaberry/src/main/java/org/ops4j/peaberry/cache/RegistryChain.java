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

package org.ops4j.peaberry.cache;

import java.util.Arrays;
import java.util.Iterator;

import javax.inject.Inject;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;

/**
 * A {@link ServiceRegistry} that delegates to a series of other registries.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class RegistryChain
    implements ServiceRegistry {

  private final ServiceRegistry[] registries;

  @Inject
  public RegistryChain(@Chain final ServiceRegistry mainRegistry,
      @Chain final ServiceRegistry[] extraRegistries) {

    // merge main and additional registries into a single array
    registries = new ServiceRegistry[1 + extraRegistries.length];
    System.arraycopy(extraRegistries, 0, registries, 1, extraRegistries.length);
    registries[0] = mainRegistry;
  }

  public final <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {

    @SuppressWarnings("unchecked")
    final Iterable<Import<T>>[] lazyIterables = new Iterable[registries.length];

    // support lazy lookup from multiple registries
    for (int i = 0; i < registries.length; i++) {
      final ServiceRegistry reg = registries[i];
      lazyIterables[i] = new Iterable<Import<T>>() { // NOPMD
            private volatile Iterable<Import<T>> iterable;

            // delay lookup until absolutely necessary
            public Iterator<Import<T>> iterator() {
              if (null == iterable) {
                synchronized (this) {
                  if (null == iterable) {
                    iterable = reg.lookup(clazz, filter);
                  }
                }
              }
              return iterable.iterator();
            }
          };
    }

    // chain the iterators together
    return new Iterable<Import<T>>() {
      public Iterator<Import<T>> iterator() {
        return new IteratorChain<Import<T>>(lazyIterables);
      }
    };
  }

  public final <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceWatcher<? super T> watcher) {

    for (final ServiceRegistry r : registries) {
      try {
        r.watch(clazz, filter, watcher); // attempt to watch all of them
      } catch (final UnsupportedOperationException e) {/* unable to watch */} // NOPMD
    }
  }

  public final <T> Export<T> add(final Import<T> service) {
    return registries[0].add(service); // only add to main repository
  }

  @Override
  public final String toString() {
    return String.format("RegistryChain%s", Arrays.toString(registries));
  }

  @Override
  public final int hashCode() {
    return Arrays.hashCode(registries);
  }

  @Override
  public final boolean equals(final Object rhs) {
    if (rhs instanceof RegistryChain) {
      return Arrays.equals(registries, ((RegistryChain) rhs).registries);
    }
    return false;
  }
}
