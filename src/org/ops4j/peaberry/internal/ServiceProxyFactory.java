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

import static org.ops4j.peaberry.internal.ConcurrentCacheFactory.newSoftCache;
import static org.ops4j.peaberry.internal.ImportProxyClassLoader.getProxyConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Factory methods for dynamic service proxies.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  // instances not allowed
  private ServiceProxyFactory() {}

  static <S, T extends S> Iterable<T> serviceProxies(final Class<T> clazz,
      final Iterable<Import<T>> services, final ImportDecorator<S> decorator) {

    // local cache of provided proxy instances, so they can be re-used
    final ConcurrentMap<Import<?>, T> PROXY_CACHE = newSoftCache();
    final Constructor<T> ctor = getProxyConstructor(clazz);

    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return new Iterator<T>() {

          // original service iterator, provided by the registry
          private final Iterator<Import<T>> i = services.iterator();

          public boolean hasNext() {
            return i.hasNext();
          }

          public T next() {
            final Import<T> service = i.next();
            T proxy = PROXY_CACHE.get(service);
            if (null == proxy) {
              final T newProxy = buildProxy(ctor, decorator, service);
              proxy = PROXY_CACHE.putIfAbsent(service, newProxy);
              if (null == proxy) {
                return newProxy;
              }
            }
            return proxy;
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }

      @Override
      public String toString() {
        final StringBuilder buf = new StringBuilder().append('[');
        String delim = "";
        for (final T t : this) {
          buf.append(delim).append(t);
          delim = ", ";
        }
        return buf.append(']').toString();
      }
    };
  }

  static <S, T extends S> T serviceProxy(final Class<T> clazz, final Iterable<Import<T>> services,
      final ImportDecorator<S> decorator) {

    // provide concurrent access to the head of the import list
    final Import<T> lookup = new ConcurrentImport<T>(services);

    // can now wrap our delegating import as a decorated dynamic proxy
    return buildProxy(getProxyConstructor(clazz), decorator, lookup);
  }

  static <S, T extends S> T buildProxy(final Constructor<T> constructor,
      final ImportDecorator<S> decorator, final Import<T> service) {

    try {
      // minimize wrapping of exceptions to help with problem determination
      return constructor.newInstance(null == decorator ? service : decorator.decorate(service));
    } catch (final InstantiationException e) {
      throw new ServiceException(e);
    } catch (final IllegalAccessException e) {
      throw new ServiceException(e);
    } catch (final InvocationTargetException e) {
      throw new ServiceException(e);
    }
  }
}
