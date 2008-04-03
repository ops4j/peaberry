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
import static org.ops4j.peaberry.internal.ServiceProxyFactory.getMultiServiceProxy;
import static org.ops4j.peaberry.internal.ServiceProxyFactory.getUnaryServiceProxy;

import java.lang.reflect.Type;

import org.ops4j.peaberry.Leased;
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
   * Create a new {@link Service} {@link Provider} for the target member.
   * 
   * @param registry dynamic service registry
   * @param target literal type of the member being injected
   * @param spec custom service specification
   * @param leased optionally leased
   * @return {@link Service} {@link Provider} for the target
   */
  @SuppressWarnings("unchecked")
  public static <T> Provider<T> getServiceProvider(ServiceRegistry registry,
      TypeLiteral<T> target, Service spec, Leased leased) {

    Type targetType = target.getType();

    final ServiceRegistry leasedRegistry =
        getLeasedServiceRegistry(registry, leased);

    final Class<?> serviceType = getServiceType(targetType);
    final String filter = getServiceFilter(spec, serviceType);

    if (expectsSequence(targetType)) {
      return new Provider() {
        public Iterable get() {
          return getMultiServiceProxy(leasedRegistry, serviceType, filter);
        }
      };
    } else {
      return new Provider() {
        public Object get() {
          return getUnaryServiceProxy(leasedRegistry, serviceType, filter);
        }
      };
    }
  }

  private static ServiceRegistry getLeasedServiceRegistry(
      ServiceRegistry registry, Leased leased) {

    if (null == leased || leased.seconds() == 0) {
      return registry;
    } else if (leased.seconds() > 0) {
      return new LeasedServiceRegistry(registry, leased);
    } else {
      return new StaticServiceRegistry(registry);
    }
  }
}
