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

import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * Factory methods for various types of dynamic service proxies.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  // instances not allowed
  private ServiceProxyFactory() {}

  public static <T> T serviceProxy(final Class<? extends T> clazz, final Import<T> handle) {

    final ClassLoader loader = clazz.getClassLoader();
    final Class<?>[] interfaces = {clazz};

    final Object proxy = newProxyInstance(loader, interfaces, new InvocationHandler() {
      public Object invoke(@SuppressWarnings("unused")
      final Object unused, final Method method, final Object[] args) {
        try {
          final Object service = handle.get();
          return method.invoke(service, args);
        } catch (final Exception e) {
          throw new ServiceUnavailableException(e);
        } finally {
          handle.unget();
        }
      }
    });

    return clazz.cast(proxy);
  }

  public static <T> Iterable<T> serviceProxies(final Class<? extends T> clazz,
      final Iterable<Import<T>> handles) {

    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return new Iterator<T>() {

          private final Iterator<Import<T>> i = handles.iterator();

          public boolean hasNext() {
            return i.hasNext();
          }

          public T next() {
            return serviceProxy(clazz, i.next());
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  public static <T> T serviceProxy(final Class<? extends T> clazz, final Iterable<Import<T>> handles) {

    return serviceProxy(clazz, new Import<T>() {
      private Import<T> handle;

      public T get() {
        handle = handles.iterator().next();
        return handle.get();
      }

      public void unget() {
        if (handle != null) {
          handle.unget();
        }
      }
    });
  }
}
