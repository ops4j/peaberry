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
 * Default {@link DynamicServiceBuilder} implementation.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class DynamicServiceBuilderImpl<T>
    implements DynamicServiceBuilder<T> {

  // primary configuration item
  final Class<? extends T> clazz;

  // default configuration
  boolean direct = false;
  String filter = null;

  // custom configuration keys
  Key<? extends ImportDecorator<? super T>> decoratorKey = null;
  Key<? extends ServiceRegistry> registryKey = null;

  public DynamicServiceBuilderImpl(final Class<? extends T> clazz) {
    this.clazz = clazz;
  }

  public DecoratedServiceBuilder<T> direct() {
    direct = true;
    return this;
  }

  public FilteredServiceBuilder<T> decoratedWith(final Key<? extends ImportDecorator<? super T>> key) {
    decoratorKey = key;
    return this;
  }

  public ScopedServiceBuilder<T> filter(final String customFilter) {
    // provide some basic normalisation of LDAP filter strings
    if (null == customFilter || customFilter.charAt(0) == '(') {
      filter = customFilter;
    } else {
      filter = '(' + customFilter + ')';
    }
    return this;
  }

  public ServiceProxyBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    registryKey = key;
    return this;
  }

  public Provider<T> single() {
    return new Provider<T>() {

      @Inject
      Injector injector;

      public T get() {

        final ImportDecorator<? super T> decorator = getDecorator(injector);
        final ServiceRegistry registry = getRegistry(injector);

        if (direct) {
          return directService(registry.lookup(clazz, filter), decorator);
        }

        return serviceProxy(clazz, registry.lookup(clazz, filter), decorator);
      }
    };
  }

  public Provider<Iterable<T>> multiple() {
    return new Provider<Iterable<T>>() {

      @Inject
      Injector injector;

      public Iterable<T> get() {

        final ImportDecorator<? super T> decorator = getDecorator(injector);
        final ServiceRegistry registry = getRegistry(injector);

        if (direct) {
          return directServices(registry.lookup(clazz, filter), decorator);
        }

        return serviceProxies(clazz, registry.lookup(clazz, filter), decorator);
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
    return injector.getInstance(ServiceRegistry.class);
  }
}
