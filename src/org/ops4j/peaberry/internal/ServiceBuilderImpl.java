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

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.builders.DecoratedServiceBuilder;
import org.ops4j.peaberry.builders.ExportProvider;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.builders.ProxyProvider;
import org.ops4j.peaberry.builders.QualifiedServiceBuilder;
import org.ops4j.peaberry.builders.ScopedServiceBuilder;
import org.ops4j.peaberry.builders.ServiceBuilder;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Default {@code DecoratedServiceBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@SuppressWarnings("unchecked")
public final class ServiceBuilderImpl<T>
    implements DecoratedServiceBuilder<T> {

  private final ServiceSettings<T> settings;

  public ServiceBuilderImpl(final Key<? extends T> key) {
    settings = new ServiceSettings(key);
  }

  public ServiceBuilderImpl(final T instance) {
    settings = new ServiceSettings(instance);
  }

  public QualifiedServiceBuilder<T> decoratedWith(
      final Key<? extends ImportDecorator<? super T>> key) {
    settings.setDecorator(new Setting(key));
    return this;
  }

  public QualifiedServiceBuilder<T> decoratedWith(final ImportDecorator<? super T> instance) {
    settings.setDecorator(new Setting(instance));
    return this;
  }

  public ScopedServiceBuilder<T> attributes(final Key<? extends Map<String, ?>> key) {
    settings.setAttributes(new Setting(key));
    return this;
  }

  public ScopedServiceBuilder<T> attributes(final Map<String, ?> instance) {
    settings.setAttributes(new Setting(instance));
    return this;
  }

  public ScopedServiceBuilder<T> filter(final Key<? extends AttributeFilter> key) {
    settings.setFilter(new Setting(key));
    return this;
  }

  public ScopedServiceBuilder<T> filter(final AttributeFilter instance) {
    settings.setFilter(new Setting(instance));
    return this;
  }

  public ServiceBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    settings.setRegistry(new Setting(key));
    return this;
  }

  public ServiceBuilder<T> in(final ServiceRegistry instance) {
    settings.setRegistry(new Setting(instance));
    return this;
  }

  public ProxyProvider<T> single() {
    final ServiceSettings<T> s = settings.clone();
    return new ProxyProvider<T>() {

      @Inject
      Injector injector;

      public T get() {
        return serviceProxy(s.getRawType(), s.getImports(injector), s.getDecorator(injector));
      }

      public Provider<T> direct() {
        return new Provider<T>() {

          @Inject
          Injector injector2;

          public T get() {
            return directService(s.getImports(injector2), s.getDecorator(injector2));
          }
        };
      }
    };
  }

  public ProxyProvider<Iterable<T>> multiple() {
    final ServiceSettings<T> s = settings.clone();
    return new ProxyProvider<Iterable<T>>() {

      @Inject
      Injector injector;

      public Iterable<T> get() {
        return serviceProxies(s.getRawType(), s.getImports(injector), s.getDecorator(injector));
      }

      public Provider<Iterable<T>> direct() {
        return new Provider<Iterable<T>>() {

          @Inject
          Injector injector2;

          public Iterable<T> get() {
            return directServices(s.getImports(injector2), s.getDecorator(injector2));
          }
        };
      }
    };
  }

  public ExportProvider<T> export() {
    final ServiceSettings<T> s = settings.clone();
    return new ExportProvider<T>() {

      @Inject
      Injector injector;

      public Export<T> get() {
        return s.getExport(injector);
      }

      public Provider<T> direct() {
        return new Provider<T>() {

          @Inject
          Injector injector2;

          public T get() {
            return s.getExport(injector2).get();
          }
        };
      }
    };
  }
}
