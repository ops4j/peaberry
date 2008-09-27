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

  private final ServiceSettings<T> settings;

  public DecoratedServiceBuilderImpl(final Class<? extends T> clazz) {
    settings = new ServiceSettings<T>(clazz);
  }

  public FilteredServiceBuilder<T> decoratedWith(final Key<? extends ImportDecorator<? super T>> key) {
    settings.setDecorator(new Setting<ImportDecorator<? super T>>(key));
    return this;
  }

  public FilteredServiceBuilder<T> decoratedWith(final ImportDecorator<? super T> instance) {
    settings.setDecorator(new Setting<ImportDecorator<? super T>>(instance));
    return this;
  }

  public ScopedServiceBuilder<T> filter(final Key<? extends AttributeFilter> key) {
    settings.setFilter(new Setting<AttributeFilter>(key));
    return this;
  }

  public ScopedServiceBuilder<T> filter(final AttributeFilter instance) {
    settings.setFilter(new Setting<AttributeFilter>(instance));
    return this;
  }

  public DynamicServiceBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    settings.setRegistry(new Setting<ServiceRegistry>(key));
    return this;
  }

  public DynamicServiceBuilder<T> in(final ServiceRegistry instance) {
    settings.setRegistry(new Setting<ServiceRegistry>(instance));
    return this;
  }

  public ServiceProxyBuilder<T> direct() {
    settings.setDirect();
    return this;
  }

  public Provider<T> single() {
    final ServiceSettings<T> service = settings.clone();
    return new Provider<T>() {

      @Inject
      Injector injector;

      public T get() {
        return service.single(injector);
      }
    };
  }

  public Provider<Iterable<T>> multiple() {
    final ServiceSettings<T> service = settings.clone();
    return new Provider<Iterable<T>>() {

      @Inject
      Injector injector;

      public Iterable<T> get() {
        return service.multiple(injector);
      }
    };
  }
}
