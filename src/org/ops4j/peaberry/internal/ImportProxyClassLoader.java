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
import static org.ops4j.peaberry.internal.ImportGlue.generateProxy;
import static org.ops4j.peaberry.internal.ImportGlue.getClazzName;
import static org.ops4j.peaberry.internal.ImportGlue.getProxyName;

import java.lang.reflect.Constructor;
import java.security.PrivilegedAction;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;

import com.google.common.collect.ReferenceMap;

/**
 * Custom classloaders that provide optimised proxies for imported services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ImportProxyClassLoader
    extends ClassLoader {

  @SuppressWarnings("unchecked")
  public static <T> Constructor<T> getProxyConstructor(final Class<? extends T> clazz) {
    try {
      // use a different custom classloader for each class space, to avoid leaks
      final ClassLoader proxyLoader = getProxyClassLoader(clazz.getClassLoader());
      final Class<?> proxyClazz = proxyLoader.loadClass(getProxyName(clazz.getName()));
      return (Constructor<T>) proxyClazz.getConstructor(Import.class);
    } catch (final LinkageError e) {
      throw new ServiceException(e);
    } catch (final Exception e) {
      throw new ServiceException(e);
    }
  }

  // weak reference map, to allow eager collection of proxy-generated classes
  private static final ReferenceMap<ClassLoader, ClassLoader> PROXY_LOADER_MAP =
      new ReferenceMap<ClassLoader, ClassLoader>(WEAK, WEAK);

  private static ClassLoader getProxyClassLoader(final ClassLoader typeLoader) {
    final ClassLoader parent = null == typeLoader ? getSystemClassLoader() : typeLoader;
    ClassLoader proxyLoader;

    synchronized (PROXY_LOADER_MAP) {
      proxyLoader = PROXY_LOADER_MAP.get(parent);
      if (null == proxyLoader) {
        proxyLoader = doPrivileged(new PrivilegedAction<ClassLoader>() {
          public ClassLoader run() {
            return new ImportProxyClassLoader(parent);
          }
        });
        PROXY_LOADER_MAP.put(parent, proxyLoader);
      }
    }

    return proxyLoader;
  }

  ImportProxyClassLoader(final ClassLoader parent) {
    super(parent);
  }

  @Override
  protected Class<?> findClass(final String clazzOrProxyName)
      throws ClassNotFoundException {

    // generated proxy will need to access Import class
    if (clazzOrProxyName.equals(Import.class.getName())) {
      return Import.class;
    }

    final String clazzName = getClazzName(clazzOrProxyName);

    // is this a new proxy class request?
    if (!clazzName.equals(clazzOrProxyName)) {
      final byte[] byteCode = generateProxy(loadClass(clazzName));
      return defineClass(clazzOrProxyName, byteCode, 0, byteCode.length);
    }

    // ignore any non-proxy requests
    throw new ClassNotFoundException();
  }
}
