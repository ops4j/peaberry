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

import static org.ops4j.peaberry.eclipse.ExtensionBeanFactory.getElementClass;
import static org.ops4j.peaberry.eclipse.ExtensionBeanFactory.getElementKey;
import static org.ops4j.peaberry.eclipse.ExtensionBeanFactory.getElementValue;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ops4j.peaberry.ServiceException;
import org.osgi.framework.Bundle;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExtensionBeanHandler
    implements InvocationHandler {

  private final ConcurrentHashMap<Method, Object> cache;
  private final IConfigurationElement config;

  public ExtensionBeanHandler(final IConfigurationElement config) {
    cache = new ConcurrentHashMap<Method, Object>();
    this.config = config;
  }

  public Object invoke(final Object proxy, final Method method, final Object[] args)
      throws Throwable {

    Object result = cache.get(method);
    if (result != null) {
      return result;
    }

    if (method.getDeclaringClass() == Object.class) {
      if (args != null) {
        args[0] = unwrapExtensionBeanProxy(args[0]); // equals
      }
      result = method.invoke(config, args); // hashCode, etc...
    } else if (null == args) {
      result = invokeGetter(method);
    } else {
      throw new UnsupportedOperationException(method.toString());
    }

    if (result != null && null == args && !method.getName().startsWith("create")) {
      cache.putIfAbsent(method, result);
    }

    return result;
  }

  private static Object unwrapExtensionBeanProxy(final Object instance) {
    if (Proxy.isProxyClass(instance.getClass())) {
      final Object handler = Proxy.getInvocationHandler(instance);
      if (handler instanceof ExtensionBeanHandler) {
        return ((ExtensionBeanHandler) handler).config;
      }
    }
    return instance;
  }

  private Object invokeGetter(final Method method) {

    final String element = findElement(method);
    final String key = getElementKey(method, element);
    final String value = getElementValue(config, key);

    final Class<?> resultType = method.getReturnType();

    if (String.class == resultType) {
      return value;
    } else if (Class.class == resultType) {
      return getElementClass(config, value);
    } else if (Bundle.class == resultType) {
      return ContributorFactoryOSGi.resolve(config.getContributor());
    } else if (IConfigurationElement.class == resultType) {
      return config;
    } else if (resultType.isPrimitive()) {
      return valueOf(resultType, value);
    }

    final IConfigurationElement[] kids = config.getChildren(key);

    if (resultType.isArray()) {
      final Class<?> componentType = resultType.getComponentType();
      final Object[] results = (Object[]) Array.newInstance(componentType, kids.length);
      for (int i = 0; i < kids.length; i++) {
        results[i] = ExtensionBeanFactory.newInstance(componentType, kids[i]);
      }
    } else if (null == value && kids.length > 0) {
      return ExtensionBeanFactory.newInstance(resultType, kids[0]);
    }

    try {
      return config.createExecutableExtension(key);
    } catch (CoreException e) {
      throw new ServiceException(e);
    }
  }

  private static String findElement(Method method) {
    // TODO Auto-generated method stub
    return null;
  }

  private static Object valueOf(Class<?> clazz, String value) {
    // TODO Auto-generated method stub
    return null;
  }
}
