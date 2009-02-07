/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.eclipse;

import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.reflect.AnnotatedElement;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ops4j.peaberry.ServiceException;
import org.osgi.framework.Bundle;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExtensionBeanFactory {

  private static final String CONTENT_KEY = "text()";

  // instances not allowed
  private ExtensionBeanFactory() {}

  public static Object newInstance(final Class<?> clazz, final IConfigurationElement config) {
    try {
      return newExtensionImpl(clazz, config);
    } catch (final RuntimeException re) {/* fall back to proxy */} // NOPMD

    final ClassLoader loader = clazz.getClassLoader();
    final Class<?>[] api = new Class[]{clazz};

    return newProxyInstance(loader, api, new ExtensionBeanHandler(config));
  }

  static Object newExtensionImpl(final Class<?> clazz, final IConfigurationElement config) {

    final String clazzKey = getElementKey(clazz, "class");
    final String clazzName = getElementValue(config, clazzKey);

    if (!clazz.isAssignableFrom(getElementClass(config, clazzName))) {
      throw new ClassCastException(clazz + " is not assignable from: " + clazzName);
    }

    try {
      return config.createExecutableExtension(clazzKey);
    } catch (final CoreException e) {
      throw new ServiceException(e);
    }
  }

  static String getElementKey(final AnnotatedElement type, final String element) {
    if (type.isAnnotationPresent(MapContent.class)) {
      return CONTENT_KEY;
    }
    final MapName mapName = type.getAnnotation(MapName.class);
    return null == mapName || mapName.value().isEmpty() ? element : mapName.value();
  }

  static String getElementValue(final IConfigurationElement config, final String elementKey) {
    return CONTENT_KEY.equals(elementKey) ? config.getValue() : config.getAttribute(elementKey);
  }

  static Class<?> getElementClass(final IConfigurationElement config, final String clazzName) {
    final Bundle bundle = ContributorFactoryOSGi.resolve(config.getContributor());
    if (null == bundle) {
      throw new ServiceException("Missing bundle context");
    }
    try {
      return bundle.loadClass(clazzName.replaceFirst(":.*$", ""));
    } catch (final ClassNotFoundException e) {
      throw new ServiceException(e);
    }
  }
}
