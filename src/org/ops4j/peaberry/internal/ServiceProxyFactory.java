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

import static org.ops4j.peaberry.internal.ImportProxyClassLoader.getProxyConstructor;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Factory methods for dynamic service proxies.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  static final ServiceUnavailableException NO_SERVICE = new ServiceUnavailableException();

  // instances not allowed
  private ServiceProxyFactory() {}

  public static <S, T extends S> Iterable<T> serviceProxies(final Class<? extends T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> decorator) {

    final Constructor<T> ctor = getProxyConstructor(clazz);

    return new Iterable<T>() {
      final Map<Import<T>, T> proxyCache = new WeakHashMap<Import<T>, T>();

      public Iterator<T> iterator() {
        return new Iterator<T>() {

          // original service iterator, provided by the registry
          private final Iterator<Import<T>> i = handles.iterator();

          public boolean hasNext() {
            return i.hasNext();
          }

          public T next() {
            final Import<T> handle = i.next();
            T proxy = proxyCache.get(handle);

            if (null == proxy) {
              // wrap each element as a decorated dynamic proxy
              proxy = buildProxy(ctor, apply(decorator, handle));
              proxyCache.put(handle, proxy);
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

  public static <S, T extends S> T serviceProxy(final Class<? extends T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> decorator) {

    /*
     * Provide an import handle that dynamically delegates to the first service,
     * but also tracks its use (even across multiple threads) so that unget() is
     * always called on the same handle as get() was originally.
     * 
     * The solution below will use the same handle until no threads are actively
     * using the injected instance. This might keep a service in use for longer
     * than expected when there is heavy contention, but it doesn't require any
     * use of thread locals or additional context stacks.
     */
    final Import<T> lookup = new Import<T>() {

      private Import<T> handle;
      private T instance;
      private int count;

      // need barrier on entry...
      public synchronized T get() {
        count++;
        if (null == handle) {
          // first valid handle may appear at any time
          final Iterator<Import<T>> i = handles.iterator();
          if (i.hasNext()) {
            handle = i.next();
            instance = handle.get(); // only called once
          }
        }
        if (null == instance) {
          throw NO_SERVICE;
        }
        return instance;
      }

      public synchronized Map<String, ?> attributes() {
        return instance != null ? handle.attributes() : null;
      }

      public synchronized void unget() {
        // last thread to exit does the unget...
        if (0 == --count && null != handle) {
          final Import<T> h = handle;
          instance = null;
          handle = null;
          h.unget();
        }
      }
    };

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
