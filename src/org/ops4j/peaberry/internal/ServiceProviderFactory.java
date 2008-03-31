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

package org.ops4j.peaberry.internal;

import static org.ops4j.peaberry.internal.ServiceFilterFactory.expectsSequence;
import static org.ops4j.peaberry.internal.ServiceFilterFactory.getServiceFilter;
import static org.ops4j.peaberry.internal.ServiceFilterFactory.getServiceType;
import static org.ops4j.peaberry.internal.ServiceProxyFactory.getServiceProxy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.ServiceRegistry;

import com.google.inject.Provider;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceProviderFactory {

  private ServiceProviderFactory() {
    // don't allow instances of helper class
  }

  /**
   * Service {@link Provider} that provides a dynamic proxy.
   */
  private interface ServiceProvider<T>
      extends Provider<T> {

    T resolve(); // resolves to actual service, not the proxy
  }

  public static <T> T resolve(Provider<T> provider) {
    if (provider instanceof ServiceProvider) {
      return ((ServiceProvider<T>) provider).resolve();
    } else {
      return provider.get();
    }
  }

  @SuppressWarnings("unchecked")
  public static Provider getServiceProvider(final ServiceRegistry registry,
      Type memberType, Service spec) {

    final Class<?> type = getServiceType(memberType);
    final String filter = getServiceFilter(spec, memberType);

    if (expectsSequence(memberType)) {

      return new ServiceProvider() {
        public Iterable get() {
          return new Iterable() {
            // fresh lookup each time
            public Iterator iterator() {
              return registry.lookup(type, filter);
            }
          };
        }

        public Iterable resolve() {
          // provide static collection of services
          return asCollection(registry.lookup(type, filter));
        }
      };
    } else {

      return new ServiceProvider() {
        public Object get() {
          // provide dynamic dispatch to service instance
          return getServiceProxy(registry, type, filter);
        }

        public Object resolve() {
          // provide static instance of service
          return registry.lookup(type, filter).next();
        }
      };
    }
  }

  /**
   * Utility method to wrap an iterator up as a collection
   */
  private static <T> Collection<T> asCollection(Iterator<T> iterator) {
    Collection<T> collection = new ArrayList<T>();
    for (; iterator.hasNext();) {
      collection.add(iterator.next());
    }
    return collection;
  }
}
