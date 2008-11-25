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

import static com.google.common.base.ReferenceType.STRONG;
import static com.google.common.base.ReferenceType.WEAK;
import static org.ops4j.peaberry.internal.ImportProxyClassLoader.getProxyConstructor;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;

import com.google.common.collect.ReferenceMap;

/**
 * Factory methods for dynamic service proxies.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  static final ServiceException NO_SERVICE = new ServiceUnavailableException();

  static final ReferenceMap<Import<?>, Object> PROXY_CACHE =
      new ReferenceMap<Import<?>, Object>(WEAK, STRONG);

  // instances not allowed
  private ServiceProxyFactory() {}

  public static <S, T extends S> Iterable<T> serviceProxies(final Class<T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> decorator) {

    final Constructor<T> ctor = getProxyConstructor(clazz);

    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return new Iterator<T>() {

          // original service iterator, provided by the registry
          private final Iterator<Import<T>> i = handles.iterator();

          public boolean hasNext() {
            return i.hasNext();
          }

          @SuppressWarnings("unchecked")
          public T next() {
            final Import<T> handle = i.next();

            T proxy = (T) PROXY_CACHE.get(handle);
            if (null == proxy) {
              // wrap each element as a decorated dynamic proxy
              final T newProxy = buildProxy(ctor, apply(decorator, handle));
              proxy = (T) PROXY_CACHE.putIfAbsent(handle, newProxy);
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
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        String delim = "";
        for (final T t : this) {
          buf.append(delim).append(t);
          delim = ", ";
        }
        return buf.append(']').toString();
      }
    };
  }

  public static <S, T extends S> T serviceProxy(final Class<T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> decorator) {

    // provide concurrent access to the head of the import list
    final Import<T> lookup = new ConcurrentImport<T>(handles);

    // we can now wrap our delegating import as a decorated dynamic proxy
    return buildProxy(getProxyConstructor(clazz), apply(decorator, lookup));
  }

  static <T> T buildProxy(final Constructor<T> constructor, final Import<T> handle) {
    try {
      return constructor.newInstance(handle);
    } catch (final Exception e) {
      throw new ServiceException(e);
    }
  }

  static <S, T extends S> Import<T> apply(final ImportDecorator<S> decorator, final Import<T> handle) {
    return null == decorator ? handle : decorator.decorate(handle);
  }
}
