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
import java.util.Arrays;
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

  final Matcher<? super Method> methodMatcher;
  final MethodInterceptor[] interceptors;

  public InterceptingDecorator(final Matcher<? super Method> methodMatcher,
      final MethodInterceptor... interceptors) {
    this.methodMatcher = methodMatcher;
    this.interceptors = interceptors;
  }

  // use JDK proxy for simplicity
  private class ProxyImport<T>
      implements Import<T>, InvocationHandler {

    private final Import<T> handle;
    private volatile T proxy;

    ProxyImport(final Import<T> handle) {
      this.handle = handle;
    }

    @SuppressWarnings("unchecked")
    public T get() {
      if (null == proxy) {
        synchronized (this) {
          try {
            final T instance = handle.get();
            if (null == proxy && instance != null) {
              // lazily-create proxy, only needs to be created once per handle
              final ClassLoader loader = instance.getClass().getClassLoader();
              proxy = (T) Proxy.newProxyInstance(loader, getInterfaces(instance), this);
            }
          } finally {
            handle.unget();
          }
        }
      }
      return proxy;
    }

    public Map<String, ?> attributes() {
      return handle.attributes();
    }

    public void unget() {/* proxy does the cleanup */}

    public Object invoke(final Object unused, final Method method, final Object[] args)
        throws Throwable {
      try {
        final Object instance = handle.get();
        if (null == instance) {
          // just in case a decorator returns null
          throw new ServiceUnavailableException();
        }

        // only intercept interesting methods
        if (!methodMatcher.matches(method)) {
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
        handle.unget();
      }
    }
  }

  // collect together all possible interfaces from the service
  static Class<?>[] getInterfaces(final Object instance) {
    @SuppressWarnings("unchecked")
    final Set<Class> api = new HashSet<Class>();
    Class<?> clazz = instance.getClass();
    while (clazz != null) {
      if (clazz.isInterface()) {
        api.add(clazz);
      } else {
        api.addAll(Arrays.asList(clazz.getInterfaces()));
      }
      clazz = clazz.getSuperclass();
    }
    return api.toArray(new Class[api.size()]);
  }

  public <T extends S> Import<T> decorate(final Import<T> handle) {
    return new ProxyImport<T>(handle);
  }
}
