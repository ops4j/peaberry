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

import static org.ops4j.peaberry.internal.DirectServiceFactory.directService;
import static org.ops4j.peaberry.internal.DirectServiceFactory.directServices;
import static org.ops4j.peaberry.internal.ServiceProxyFactory.serviceProxies;
import static org.ops4j.peaberry.internal.ServiceProxyFactory.serviceProxy;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.builders.DecoratedServiceBuilder;
import org.ops4j.peaberry.builders.DynamicServiceBuilder;
import org.ops4j.peaberry.builders.FilteredServiceBuilder;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.builders.ScopedServiceBuilder;
import org.ops4j.peaberry.builders.ServiceProxyBuilder;

import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Default {@code DecoratedServiceBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class DecoratedServiceBuilderImpl<T>
    implements DecoratedServiceBuilder<T> {

  private final Class<? extends T> serviceClazz;
  private final Configuration<T> config;

  // mutable builder settings
  static class Configuration<T> {
    AttributeFilter attributeFilter;
    boolean isDirect = false;
    Key<? extends ImportDecorator<? super T>> decoratorKey;
    Key<? extends ServiceRegistry> registryKey;
  }

  public DecoratedServiceBuilderImpl(final Class<? extends T> clazz) {
    config = new Configuration<T>();
    serviceClazz = clazz;
  }

  public FilteredServiceBuilder<T> decoratedWith(final Key<? extends ImportDecorator<? super T>> key) {
    config.decoratorKey = key;
    return this;
  }

  public ScopedServiceBuilder<T> filter(final AttributeFilter filter) {
    config.attributeFilter = filter;
    return this;
  }

  public DynamicServiceBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    config.registryKey = key;
    return this;
  }

  public ServiceProxyBuilder<T> direct() {
    config.isDirect = true;
    return this;
  }

  public Provider<T> single() {
    return new AbstractServiceProvider<T, T>(serviceClazz, config) {
      public T get() {
        if (isDirect) {
          return directService(getServices(), getDecorator());
        }
        return serviceProxy(serviceClazz, getServices(), getDecorator());
      }
    };
  }

  public Provider<Iterable<T>> multiple() {
    return new AbstractServiceProvider<T, Iterable<T>>(serviceClazz, config) {
      public Iterable<T> get() {
        if (isDirect) {
          return directServices(getServices(), getDecorator());
        }
        return serviceProxies(serviceClazz, getServices(), getDecorator());
      }
    };
  }
}
