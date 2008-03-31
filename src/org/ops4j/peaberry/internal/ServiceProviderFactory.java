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
import com.google.inject.TypeLiteral;

/**
 * Provide dynamic {@link Service} {@link Provider}s.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceProviderFactory {

  // utility: instances not allowed
  private ServiceProviderFactory() {}

  /**
   * {@link Service} {@link Provider} that provides a dynamic proxy.
   */
  private interface ServiceProvider<T>
      extends Provider<T> {

    T resolve(); // resolves to the actual service, not the proxy
  }

  /**
   * Resolves to the current service instance, not the dynamic proxy.
   * 
   * @param provider service provider
   * @return current service instance
   */
  public static <T> T resolve(Provider<T> provider) {
    if (provider instanceof ServiceProvider) {
      return ((ServiceProvider<T>) provider).resolve();
    } else {
      return provider.get();
    }
  }

  /**
   * Create a new {@link Service} {@link Provider} for the target member.
   * 
   * @param registry dynamic service registry
   * @param target literal type of the member being injected
   * @param spec annotation details for the injected service
   * @return {@link Service} {@link Provider} for the target
   */
  @SuppressWarnings("unchecked")
  public static <T> Provider<T> getServiceProvider(
      final ServiceRegistry registry, TypeLiteral<T> target, Service spec) {

    // runtime type of the injectee
    Type targetType = target.getType();

    // determine service proxy configuration
    final Class<?> serviceType = getServiceType(targetType);
    final String filter = getServiceFilter(spec, serviceType);

    if (expectsSequence(targetType)) {

      // multiple service (..N)
      return new ServiceProvider() {

        public Iterable get() {
          return new Iterable() {
            // fresh lookup each time
            public Iterator iterator() {
              return registry.lookup(serviceType, filter);
            }
          };
        }

        public Iterable resolve() {
          // provide static collection of currently available services
          return asCollection(registry.lookup(serviceType, filter));
        }
      };
    } else {

      // unary service (..1)
      return new ServiceProvider() {

        public Object get() {
          // provide dynamic dispatch to first service instance
          return getServiceProxy(registry, serviceType, filter);
        }

        public Object resolve() {
          // provide static instance of the current service
          return registry.lookup(serviceType, filter).next();
        }
      };
    }
  }

  /**
   * Utility method to wrap an iterator up as a standard collection.
   */
  private static <T> Collection<T> asCollection(Iterator<T> iterator) {
    Collection<T> collection = new ArrayList<T>();
    for (; iterator.hasNext();) {
      collection.add(iterator.next());
    }
    return collection;
  }
}
