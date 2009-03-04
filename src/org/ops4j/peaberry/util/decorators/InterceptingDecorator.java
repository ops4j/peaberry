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

package org.ops4j.peaberry.util.decorators;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;

import com.google.inject.matcher.Matcher;

/**
 * An {@link ImportDecorator} that supports {@link MethodInterceptor}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class InterceptingDecorator<S>
    implements ImportDecorator<S> {

  final Matcher<? super Class<?>> classMatcher;
  final Matcher<? super Method> methodMatcher;
  final MethodInterceptor[] interceptors;

  public InterceptingDecorator(final Matcher<? super Class<?>> classMatcher,
      final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors) {
    this.classMatcher = classMatcher;
    this.methodMatcher = methodMatcher;
    this.interceptors = interceptors;
  }

  // use JDK proxy for simplicity
  private final class ProxyImport<T>
      implements Import<T>, InvocationHandler {

    private final Import<T> service;
    private volatile T proxy;

    ProxyImport(final Import<T> service) {
      this.service = service;
    }

    @SuppressWarnings("unchecked")
    public T get() {
      if (null == proxy) {
        synchronized (this) {
          try {
            final T instance = service.get();
            if (null == proxy && instance != null) {
              // lazily-create proxy, only needs to be created once per service
              final ClassLoader loader = instance.getClass().getClassLoader();
              proxy = (T) Proxy.newProxyInstance(loader, getInterfaces(instance), this);
            }
          } finally {
            service.unget();
          }
        }
      }
      return proxy;
    }

    public Map<String, ?> attributes() {
      return service.attributes();
    }

    public void unget() {/* proxy does the cleanup */}

    public Object invoke(final Object unused, final Method method, final Object[] args)
        throws Throwable {
      try {
        final Object instance = service.get();
        if (null == instance) {
          // just in case a decorator returns null
          throw new ServiceUnavailableException();
        }

        // only intercept interesting methods
        if (!methodMatcher.matches(method) || !classMatcher.matches(method.getDeclaringClass())) {
          return method.invoke(instance, args);
        }

        // begin chain of intercepting method invocations
        return interceptors[0].invoke(new MethodInvocation() {
          private int index = 1;

          public AccessibleObject getStaticPart() {
            return method;
          }

          public Method getMethod() {
            return method;
          }

          public Object[] getArguments() {
            return args;
          }

          public Object getThis() {
            return instance;
          }

          public Object proceed()
              throws Throwable {
            try {
              // walk down the stack of interceptors
              if (index < interceptors.length) {
                return interceptors[index++].invoke(this);
              }
              // no more interceptors, invoke service
              return method.invoke(instance, args);
            } finally {
              index--; // rollback in case called again
            }
          }
        });
      } finally {
        service.unget();
      }
    }
  }

  static Class<?>[] getInterfaces(final Object instance) {
    @SuppressWarnings("unchecked")
    final Set<Class> api = new HashSet<Class>();
    // look through the class hierarchy collecting all visible interfaces
    for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
      Collections.addAll(api, clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces());
    }
    return api.toArray(new Class[api.size()]);
  }

  public <T extends S> Import<T> decorate(final Import<T> service) {
    return new ProxyImport<T>(service);
  }
}
