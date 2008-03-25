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

import static com.google.inject.internal.ReferenceType.WEAK;
import com.google.inject.ClassLoaderHook;
import com.google.inject.internal.ReferenceCache;

/**
 * Hook to support Guice code generation inside OSGi containers.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiClassLoaderHook
    implements ClassLoaderHook {

  /**
   * Weak cache of bridge classloaders that make the Guice implementation
   * classes visible to various code-generated proxies of client classes
   */
  private final ReferenceCache<ClassLoader, ClassLoader> m_classLoaderCache =
      new ReferenceCache<ClassLoader, ClassLoader>(WEAK, WEAK) {
        private static final long serialVersionUID = 1L;

        @Override
        protected final ClassLoader create(ClassLoader parent) {
          return new BridgeClassLoader(parent);
        }
      };

  /**
   * Custom classloader that switches delegation between two parent loaders.
   */
  private static final class BridgeClassLoader
      extends ClassLoader {

    private static final String CGLIB_PACKAGE = "com.google.inject.cglib";

    private static final ClassLoader PEABERRY_LOADER =
        BridgeClassLoader.class.getClassLoader();

    /**
     * {@inheritDoc}
     */
    public BridgeClassLoader(ClassLoader parent) {
      super(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> loadClass(final String name, final boolean resolve)
        throws ClassNotFoundException {

      // delegate internal CGLIB requests to Peaberry bundle classloader
      if (PEABERRY_LOADER != null && name.startsWith(CGLIB_PACKAGE)) {
        final Class<?> clazz = PEABERRY_LOADER.loadClass(name);
        if (resolve) {
          super.resolveClass(clazz);
        }
        return clazz;
      }

      // default to standard OSGi delegation
      return super.loadClass(name, resolve);
    }
  }

  /**
   * {@inheritDoc}
   */
  public synchronized ClassLoader get(Class<?> type) {
    return m_classLoaderCache.get(type.getClassLoader());
  }
}
