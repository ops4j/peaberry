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

import static java.lang.Character.toLowerCase;
import static org.ops4j.peaberry.eclipse.ExtensionBeanFactory.getElementClass;
import static org.ops4j.peaberry.eclipse.ExtensionBeanFactory.mapName;
import static org.ops4j.peaberry.eclipse.ExtensionBeanFactory.mapContent;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.osgi.framework.Bundle;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExtensionBeanHandler
    implements InvocationHandler {

  private static final String[] PREFIXES = {"is", "get", "create"};

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
    return null;
  }

  private Object invokeGetter(final Method method) {
    final Class<?> resultType = method.getReturnType();

    if (IConfigurationElement.class == resultType) {
      return config;
    } else if (Bundle.class == resultType) {
      return ContributorFactoryOSGi.resolve(config.getContributor());
    }

    final String key = mapName(method, findElementName(method));
    final String value = mapContent(config, key);

    if (null != value) {

      if (String.class == resultType) {
        return value;
      } else if (Class.class == resultType) {
        return getElementClass(config, value);
      } else if (resultType.isPrimitive()) {
        return valueOf(resultType, value);
      }

      try {
        return config.createExecutableExtension(key);
      } catch (final CoreException e) {}
    }

    return invokeNestedGetter(resultType, key);
  }

  private Object invokeNestedGetter(final Class<?> resultType, final String key) {
    final IConfigurationElement[] kids = config.getChildren(key);

    if (resultType.isArray()) {
      final Class<?> componentType = resultType.getComponentType();
      final Object[] results = (Object[]) Array.newInstance(componentType, kids.length);
      for (int i = 0; i < kids.length; i++) {
        results[i] = ExtensionBeanFactory.newInstance(componentType, kids[i]);
      }
      return results;
    } else if (kids.length > 0) {
      return ExtensionBeanFactory.newInstance(resultType, kids[0]);
    }

    return null;
  }

  private static String findElementName(final Method method) {
    final String name = method.getName();
    for (final String prefix : PREFIXES) {
      if (name.startsWith(prefix)) {
        final int n = prefix.length();
        return toLowerCase(name.charAt(n)) + name.substring(n + 1);
      }
    }
    return name;
  }

  private static Object valueOf(final Class<?> clazz, final String value) {
    if (Boolean.class == clazz) {
      return Boolean.valueOf(value);
    } else if (Byte.class == clazz) {
      return Byte.valueOf(value);
    } else if (Character.class == clazz) {
      return value.charAt(0);
    } else if (Short.class == clazz) {
      return Short.valueOf(value);
    } else if (Integer.class == clazz) {
      return Integer.valueOf(value);
    } else if (Float.class == clazz) {
      return Float.valueOf(value);
    } else if (Long.class == clazz) {
      return Long.valueOf(value);
    } else if (Double.class == clazz) {
      return Double.valueOf(value);
    }
    throw new IllegalArgumentException("Unknown primitive type: " + clazz);
  }
}
