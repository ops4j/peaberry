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

import static java.security.AccessController.doPrivileged;
import static jsr166y.ConcurrentReferenceHashMap.ReferenceType.WEAK;
import static org.ops4j.peaberry.internal.ComputedMapFactory.computedMap;
import static org.ops4j.peaberry.internal.ImportGlue.generateProxy;
import static org.ops4j.peaberry.internal.ImportGlue.getClazzName;
import static org.ops4j.peaberry.internal.ImportGlue.getProxyName;

import java.lang.reflect.Constructor;
import java.security.PrivilegedAction;
import java.util.concurrent.ConcurrentMap;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.internal.ComputedMapFactory.Function;

/**
 * Custom classloader that provides optimized proxies for imported services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ImportProxyClassLoader
    extends ClassLoader {

  private static final String UNAVAILABLE_CLAZZ_NAME = ServiceUnavailableException.class.getName();
  private static final String IMPORT_CLAZZ_NAME = Import.class.getName();

  @SuppressWarnings("unchecked")
  static <T> Constructor<T> getProxyConstructor(final Class<T> clazz) {
    try {
      // use a different custom classloader for each class-space, to avoid leaks
      final ClassLoader proxyLoader = getProxyClassLoader(clazz.getClassLoader());
      final Class<?> proxyClazz = proxyLoader.loadClass(getProxyName(clazz.getName()));
      return (Constructor<T>) proxyClazz.getConstructor(Import.class);
    } catch (final LinkageError e) {
      throw new ServiceException(e);
    } catch (final NoSuchMethodException e) {
      throw new ServiceException(e);
    } catch (final ClassNotFoundException e) {
      throw new ServiceException(e);
    }
  }

  // weak map of classloaders, to allow eager collection of proxied classes
  private static final ConcurrentMap<ClassLoader, ClassLoader> LOADER_MAP =
      computedMap(WEAK, WEAK, 32, new Function<ClassLoader, ClassLoader>() {
        public ClassLoader compute(final ClassLoader parent) {
          return doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
              return new ImportProxyClassLoader(parent);
            }
          });
        }
      });

  private static ClassLoader getProxyClassLoader(final ClassLoader typeLoader) {
    return LOADER_MAP.get(null != typeLoader ? typeLoader : getSystemClassLoader());
  }

  // delegate to the original type's classloader
  ImportProxyClassLoader(final ClassLoader parent) {
    super(parent);
  }

  @Override
  protected Class<?> loadClass(final String name, final boolean resolve)
      throws ClassNotFoundException {

    // short-circuit access to these classes
    if (UNAVAILABLE_CLAZZ_NAME.equals(name)) {
      return ServiceUnavailableException.class;
    }
    if (IMPORT_CLAZZ_NAME.equals(name)) {
      return Import.class;
    }

    return super.loadClass(name, resolve);
  }

  @Override
  protected Class<?> findClass(final String clazzOrProxyName)
      throws ClassNotFoundException {

    final String clazzName = getClazzName(clazzOrProxyName);

    // is this a new proxy class request?
    if (!clazzName.equals(clazzOrProxyName)) {
      final byte[] code = generateProxy(loadClass(clazzName));
      return defineClass(clazzOrProxyName, code, 0, code.length);
    }

    // ignore any non-proxy requests
    throw new ClassNotFoundException(clazzOrProxyName);
  }
}
