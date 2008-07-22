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

import static java.util.Collections.emptyList;
import static org.ops4j.peaberry.internal.ImportProxyClassLoader.importProxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Factory methods for various types of dynamic service proxies.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  // instances not allowed
  private ServiceProxyFactory() {}

  public static <S, T extends S> Iterable<T> serviceProxies(final Class<? extends T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> deco, final boolean sticky) {

    if (sticky) {
      return stickyServiceProxies(clazz, handles, deco);
    }

    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return new Iterator<T>() {

          private final Iterator<Import<T>> i = handles.iterator();

          public boolean hasNext() {
            return i.hasNext();
          }

          public T next() {
            return importProxy(clazz, null == deco ? i.next() : deco.decorate(i.next()), false);
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  private static <S, T extends S> Iterable<T> stickyServiceProxies(final Class<? extends T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> deco) {

    return new Iterable<T>() {
      private volatile List<T> stickyProxies = emptyList();

      private List<T> getActiveServices() {
        final List<T> proxies = new ArrayList<T>();
        for (final Import<T> h : handles) {
          proxies.add(importProxy(clazz, null == deco ? h : deco.decorate(h), true));
        }
        return proxies;
      }

      public Iterator<T> iterator() {
        if (stickyProxies.size() == 0) {
          synchronized (this) {
            if (stickyProxies.size() == 0) {
              stickyProxies = getActiveServices();
            }
          }
        }
        return stickyProxies.iterator();
      }
    };
  }

  public static <S, T extends S> T serviceProxy(final Class<? extends T> clazz,
      final Iterable<Import<T>> handles, final ImportDecorator<S> deco, final boolean sticky) {

    final Import<T> lookup = new Import<T>() {
      private long count = 0L;
      private Import<T> handle;

      public synchronized T get() {
        count++;
        if (null == handle) {
          handle = handles.iterator().next();
        }
        return handle.get();
      }

      public synchronized void unget() {
        try {
          handle.unget();
        } finally {
          if (--count == 0) {
            handle = null;
          }
        }
      }
    };

    return importProxy(clazz, null == deco ? lookup : deco.decorate(lookup), sticky);
  }
}
