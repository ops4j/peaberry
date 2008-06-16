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

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;

import com.google.inject.internal.GuiceCodeGen;
import com.google.inject.internal.cglib.proxy.Dispatcher;
import com.google.inject.internal.cglib.proxy.Enhancer;

/**
 * Provide proxies that delegate to services found in a {@link ServiceRegistry}.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  // utility: instances not allowed
  private ServiceProxyFactory() {}

  /**
   * Create a proxy that delegates to a single service from the registry.
   * 
   * @param registry dynamic service registry
   * @param clazz expected service class
   * @param filter RFC-1960 LDAP filter
   * @param interfaces service API
   * 
   * @return proxy that delegates to the registry
   */
  public static <T> T getServiceProxy(final ServiceRegistry registry, final Class<? extends T> clazz,
      final String filter, final Class<?>... interfaces) {

    final Enhancer proxy = GuiceCodeGen.newEnhancer(clazz);
    proxy.setCallback(new Dispatcher() {
      public Object loadObject() {
        try {
          // use first matching service from the dynamic query
          return registry.lookup(clazz, filter).iterator().next();
        } catch (final Exception e) {
          throw new ServiceUnavailableException(e);
        }
      }
    });

    // apply custom service API
    if (interfaces.length > 0) {
      proxy.setInterfaces(interfaces);
    }

    return clazz.cast(proxy.create());
  }
}
