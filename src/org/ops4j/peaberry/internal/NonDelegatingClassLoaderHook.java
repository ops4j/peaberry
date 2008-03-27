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
 * Hook to support Guice inside non-delegating classloader containers like OSGi,
 * where the CGLIB proxy/reflection classes may not be visible to client types.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class NonDelegatingClassLoaderHook
    implements ClassLoaderHook {

  /**
   * Weak cache of bridge classloaders that make the Guice implementation
   * classes visible to various code-generated proxies of client classes.
   */
  private final ReferenceCache<ClassLoader, ClassLoader> m_classLoaderCache =
      new ReferenceCache<ClassLoader, ClassLoader>(WEAK, WEAK) {
        private static final long serialVersionUID = 1L;

        private static final String CGLIB_PACKAGE = "com.google.inject.cglib";

        @Override
        protected final ClassLoader create(ClassLoader typeClassLoader) {
          return new ClassLoader(typeClassLoader) {

            /**
             * Bridge between client classloader and current classloader.
             */
            @Override
            protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException {

              if (name.startsWith(CGLIB_PACKAGE)) {
                try {
                  Class<?> clazz = getClass().getClassLoader().loadClass(name);
                  if (resolve) {
                    super.resolveClass(clazz);
                  }
                  return clazz;
                } catch (Exception e) {
                  // fall back to classic delegation
                }
              }

              return super.loadClass(name, resolve);
            }
          };
        }
      };

  /**
   * Use weak cache to avoid explosion of classloaders.
   */
  public ClassLoader get(Class<?> type) {
    ClassLoader typeLoader = type.getClassLoader();

    // optimisation: no need to bridge sibling types
    if (getClass().getClassLoader() == typeLoader) {
      return typeLoader;
    } else {
      return m_classLoaderCache.get(typeLoader);
    }
  }
}
