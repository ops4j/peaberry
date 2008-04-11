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

import static com.google.inject.internal.Objects.nonNull;
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

  private static final String SERVICE_PROVIDER_DESCRIPTION =
      "%s lookup(%s,\"%s\") from %s";

  // utility: instances not allowed
  private ServiceProviderFactory() {}

  /**
   * Create a new {@link Service} {@link Provider} for the target member.
   * 
   * @param registry dynamic service registry
   * @param target literal type of the member being injected
   * @param spec custom service specification
   * @param leased optionally leased
   * 
   * @return {@link Service} {@link Provider} for the target
   */
  @SuppressWarnings("unchecked")
  public static <T> Provider<T> getServiceProvider(ServiceRegistry registry,
      TypeLiteral<T> target, final Service spec, Leased leased) {

    nonNull(registry, "service registry");
    nonNull(target, "injection target");

    Type targetType = target.getType();

    final ServiceRegistry leasedRegistry;
    if (leased != null && leased.seconds() != 0) {
      leasedRegistry = new LeasedServiceRegistry(registry, leased);
    } else {
      leasedRegistry = registry;
    }

    final Class<?> serviceType = getServiceType(targetType);
    final String filter = getServiceFilter(spec, serviceType);

    if (expectsSequence(targetType)) {
      return new Provider() {
        public Iterable get() {
          return getMultiServiceProxy(spec, leasedRegistry, serviceType, filter);
        }

        @Override
        public String toString() {
          return String.format(SERVICE_PROVIDER_DESCRIPTION,
              "MultiServiceProvider", serviceType, filter, leasedRegistry);
        }
      };
    } else {
      return new Provider() {
        public Object get() {
          return getUnaryServiceProxy(spec, leasedRegistry, serviceType, filter);
        }

        @Override
        public String toString() {
          return String.format(SERVICE_PROVIDER_DESCRIPTION,
              "UnaryServiceProvider", serviceType, filter, leasedRegistry);
        }
      };
    }
  }
}
