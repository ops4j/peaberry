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
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ImportProxyClassLoader
    extends ClassLoader {

  private static final ReferenceMap<ClassLoader, ClassLoader> PROXY_LOADER_MAP =
      new ReferenceMap<ClassLoader, ClassLoader>(WEAK, WEAK);

  public static <T> T importProxy(final Class<? extends T> clazz, final Import<?> handle,
      final boolean sticky) {

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
      final String proxyName = getProxyName(clazz.getName());
      final Class<?> proxyClazz = proxyLoader.loadClass(proxyName);
      final Constructor<?> ctor = proxyClazz.getConstructor(Object.class);
      return clazz.cast(ctor.newInstance(handle));
    } catch (final Exception e) {
      throw new ServiceException(e);
    }
  }

  ImportProxyClassLoader(final ClassLoader loader) {
    super(loader);
  }

  @Override
  protected Class<?> findClass(final String clazzOrProxyName)
      throws ClassNotFoundException {

    final String clazzName = getClazzName(clazzOrProxyName);

    if (!clazzName.equals(clazzOrProxyName)) {
      final Class<?> clazz = loadClass(clazzName);
      final byte[] byteCode = generateProxy(clazz);
      return defineClass(clazzOrProxyName, byteCode, 0, byteCode.length);
    }

    throw new ClassNotFoundException();
  }
}
