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

import static com.google.common.base.ReferenceType.WEAK;
import static java.security.AccessController.doPrivileged;

import java.lang.reflect.Constructor;
import java.security.PrivilegedAction;
import java.util.Iterator;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;

import com.google.common.collect.ReferenceMap;

/**
 * Factory methods for various types of dynamic service proxies.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ServiceProxyFactory {

  // instances not allowed
  private ServiceProxyFactory() {}

  private static final ReferenceMap<ClassLoader, ClassLoader> PROXY_LOADER_MAP =
      new ReferenceMap<ClassLoader, ClassLoader>(WEAK, WEAK);

  public static <T> T importProxy(final Class<? extends T> clazz, final Import<T> handle) {
    final ClassLoader typeLoader = clazz.getClassLoader();

    ClassLoader proxyLoader;

    synchronized (PROXY_LOADER_MAP) {
      proxyLoader = PROXY_LOADER_MAP.get(clazz.getClassLoader());
      if (null == proxyLoader) {
        proxyLoader = doPrivileged(new PrivilegedAction<ClassLoader>() {
          public ClassLoader run() {
            return new ImportProxyClassLoader(typeLoader);
          }
        });
        PROXY_LOADER_MAP.put(typeLoader, proxyLoader);
      }
    }

    try {
      final Class<?> proxyClazz = proxyLoader.loadClass(clazz.getName() + "$Peaberry");
      Constructor<?> ctor = proxyClazz.getConstructor(Import.class);
      return clazz.cast(ctor.newInstance(handle));
    } catch (Exception e) {
      throw new ServiceException(e);
    }
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
            return importProxy(clazz, i.next());
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  public static <T> T serviceProxy(final Class<? extends T> clazz, final Iterable<Import<T>> handles) {

    @SuppressWarnings("unchecked")
    Import<T> dynamicLookup = importProxy(Import.class, new Import<Import>() {
      public Import<T> get() {
        return handles.iterator().next();
      }

      public void unget() {}
    });

    return importProxy(clazz, dynamicLookup);
  }
}
