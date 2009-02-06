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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.osgi.framework.Bundle;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExtensionBeanFactory {

  // instances not allowed
  private ExtensionBeanFactory() {}

  public static Object newInstance(final Class<?> clazz, final IConfigurationElement config) {
    Object instance = null;

    try {
      instance = createExtensionInstance(clazz, config);
    } catch (final RuntimeException re) {}

    if (null == instance) {
      return newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new BeanHandler(config));
    }

    return instance;
  }

  private static Object createExtensionInstance(final Class<?> clazz,
      final IConfigurationElement config) {

    final String clazzKey = getAttributeName(clazz, "class");
    final String name = config.getAttribute(clazzKey);

    if (clazz.isAssignableFrom(getElementClass(name, config))) {
      try {
        return config.createExecutableExtension(name);
      } catch (CoreException e) {}
    }

    return null;
  }

  private static class BeanHandler
      implements InvocationHandler {

    public BeanHandler(final IConfigurationElement config) {}

    public Object invoke(final Object proxy, final Method method, final Object[] args)
        throws Throwable {
      // TODO Auto-generated method stub
      return null;
    }
  }

  private static String getAttributeName(final AnnotatedElement type, final String key) {
    final Attribute name = type.getAnnotation(Attribute.class);
    return null == name || name.value().isEmpty() ? key : name.value();
  }

  private static Class<?> getElementClass(String name, final IConfigurationElement config) {
    if (name != null) {
      final Bundle bundle = ContributorFactoryOSGi.resolve(config.getContributor());
      if (bundle != null) {
        try {
          return bundle.loadClass(name.replaceFirst(":.*$", ""));
        } catch (final ClassNotFoundException e) {}
      }
    }
    return null;
  }
}
