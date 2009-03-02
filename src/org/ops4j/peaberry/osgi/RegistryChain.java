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
import java.util.concurrent.Callable;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;

import com.google.inject.Inject;

/**
 * A {@link ServiceRegistry} that delegates to a series of other registries.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class RegistryChain
    implements ServiceRegistry {

  private final ServiceRegistry[] registries;

  @Inject
  public RegistryChain(final CachingServiceRegistry mainRegistry,
      @PrivateBinding final ServiceRegistry[] alternativeRegistries) {

    // merge main and alternative registries into a single array
    registries = new ServiceRegistry[1 + alternativeRegistries.length];
    System.arraycopy(alternativeRegistries, 0, registries, 1, alternativeRegistries.length);
    registries[0] = mainRegistry;
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {

    @SuppressWarnings("unchecked")
    final Callable<Iterator<Import<T>>>[] lazyIterators = new Callable[registries.length];

    // support lazy lookup from multiple registries
    for (int i = 0; i < lazyIterators.length; i++) {
      final ServiceRegistry reg = registries[i];
      lazyIterators[i] = new Callable<Iterator<Import<T>>>() {
        private Iterable<Import<T>> iterable;

        public synchronized Iterator<Import<T>> call() {
          if (null == iterable) {
            iterable = reg.lookup(clazz, filter);
          }
          return iterable.iterator();
        }
      };
    }

    // chain the iterators together
    return new Iterable<Import<T>>() {
      public Iterator<Import<T>> iterator() {
        return new IteratorChain<Import<T>>(lazyIterators);
      }
    };
  }

  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceWatcher<? super T> watcher) {

    for (final ServiceRegistry r : registries) {
      try {
        r.watch(clazz, filter, watcher); // attempt to watch all of them
      } catch (final UnsupportedOperationException e) {/* unable to watch */}
    }
  }

  public <T> Export<T> add(final Import<T> service) {
    return registries[0].add(service); // only add to main repository
  }
}
