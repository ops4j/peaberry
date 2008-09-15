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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Default {@code DecoratedServiceBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class DecoratedServiceBuilderImpl<T>
    implements DecoratedServiceBuilder<T> {

  // primary configuration item
  final Class<? extends T> clazz;

  // default configuration
  AttributeFilter attributeFilter;
  boolean direct = false;

  // custom configuration keys
  Key<? extends ImportDecorator<? super T>> decoratorKey;
  Key<? extends ServiceRegistry> registryKey;

  public DecoratedServiceBuilderImpl(final Class<? extends T> clazz) {
    this.clazz = clazz;
  }

  public FilteredServiceBuilder<T> decoratedWith(final Key<? extends ImportDecorator<? super T>> key) {
    decoratorKey = key;
    return this;
  }

  public ScopedServiceBuilder<T> filter(final AttributeFilter filter) {
    attributeFilter = filter;
    return this;
  }

  public DynamicServiceBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    registryKey = key;
    return this;
  }

  public ServiceProxyBuilder<T> direct() {
    direct = true;
    return this;
  }

  public Provider<T> single() {
    return new Provider<T>() {

      @Inject
      Injector injector;

      public T get() {

        // time to lookup the actual implementation bindings
        final ImportDecorator<? super T> decorator = getDecorator(injector);
        final ServiceRegistry registry = getRegistry(injector);

        if (direct) {
          return directService(registry.lookup(clazz, attributeFilter), decorator);
        }

        return serviceProxy(clazz, registry.lookup(clazz, attributeFilter), decorator);
      }
    };
  }

  public Provider<Iterable<T>> multiple() {
    return new Provider<Iterable<T>>() {

      @Inject
      Injector injector;

      public Iterable<T> get() {

        // time to lookup the actual implementation bindings
        final ImportDecorator<? super T> decorator = getDecorator(injector);
        final ServiceRegistry registry = getRegistry(injector);

        if (direct) {
          return directServices(registry.lookup(clazz, attributeFilter), decorator);
        }

        return serviceProxies(clazz, registry.lookup(clazz, attributeFilter), decorator);
      }
    };
  }

  ImportDecorator<? super T> getDecorator(final Injector injector) {
    if (decoratorKey != null) {
      return injector.getInstance(decoratorKey);
    }
    return null;
  }

  ServiceRegistry getRegistry(final Injector injector) {
    if (registryKey != null) {
      return injector.getInstance(registryKey);
    }
    // use default service registry (typically OSGi)
    return injector.getInstance(ServiceRegistry.class);
  }
}
