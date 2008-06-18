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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;

import com.google.inject.internal.GuiceCodeGen;

/**
 * Factory methods for various dynamic service proxies.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  // instances not allowed
  private ServiceProxyFactory() {}

  /**
   * Create a single service proxy that dynamically delegates to the best match.
   * 
   * @param registry service registry
   * @param clazz expected service class
   * @param filter RFC-1960 LDAP filter
   * @return proxy for single service
   */
  public static <T> T singleServiceProxy(final ServiceRegistry registry,
      final Class<? extends T> clazz, final String filter) {

    return GuiceCodeGen.newProxyInstance(clazz, new InvocationHandler() {
      public Object invoke(@SuppressWarnings("unused")
      final Object proxy, final Method method, final Object[] args) {
        try {
          // use first matching service from the dynamic query
          final T service = registry.lookup(clazz, filter).next();
          return method.invoke(service, args);
        } catch (final Exception e) {
          throw new ServiceUnavailableException(e);
        }
      }
    });
  }

  /**
   * Create a multiple service proxy that iterates over all matching services.
   * 
   * @param registry service registry
   * @param clazz expected service class
   * @param filter RFC-1960 LDAP filter
   * @return proxy for multiple services
   */
  public static <T> Iterable<T> multiServiceProxy(final ServiceRegistry registry,
      final Class<? extends T> clazz, final String filter) {

    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return registry.lookup(clazz, filter);
      }
    };
  }
}
