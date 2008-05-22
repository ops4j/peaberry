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

import java.util.Iterator;

import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;

import com.google.inject.cglib.proxy.Dispatcher;
import com.google.inject.cglib.proxy.Enhancer;
import com.google.inject.internal.GuiceCodeGen;

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
   * @param spec custom service specification
   * @param registry dynamic service registry
   * @param clazz expected service class
   * @param filter RFC-1960 LDAP filter
   * 
   * @return proxy that delegates to the registry
   */
  public static <T> T getUnaryServiceProxy(Service spec,
      final ServiceRegistry registry, final Class<T> clazz, final String filter) {

    Enhancer proxy = GuiceCodeGen.getEnhancer(clazz);
    proxy.setCallback(new Dispatcher() {
      public Object loadObject() {
        try {
          // use first matching service from registry
          return registry.lookup(clazz, filter).next();
        } catch (Exception e) {
          throw new ServiceUnavailableException(e);
        }
      }
    });

    // apply custom service API to generated proxy
    if (spec != null && spec.interfaces().length > 0) {
      proxy.setInterfaces(spec.interfaces());
    }

    return clazz.cast(proxy.create());
  }

  /**
   * Create a proxy that delegates to a sequence of services from the registry.
   * 
   * @param spec custom service specification
   * @param registry dynamic service registry
   * @param clazz expected service class
   * @param filter RFC-1960 LDAP filter
   * 
   * @return iterable proxy that delegates to the registry
   */
  public static <T> Iterable<T> getMultiServiceProxy(Service spec,
      final ServiceRegistry registry, final Class<T> clazz, final String filter) {

    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return registry.lookup(clazz, filter);
      }
    };
  }
}
