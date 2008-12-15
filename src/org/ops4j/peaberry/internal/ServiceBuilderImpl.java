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

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;
import org.ops4j.peaberry.builders.DecoratedServiceBuilder;
import org.ops4j.peaberry.builders.ExportProvider;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.builders.ProxyProvider;

import com.google.inject.Key;

/**
 * Default {@link DecoratedServiceBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class ServiceBuilderImpl<T>
    implements DecoratedServiceBuilder<T> {

  // current builder state (can be cloned)
  private final ServiceSettings<T> settings;

  public ServiceBuilderImpl(final Key<T> key) {
    settings = new ServiceSettings<T>(key);
  }

  public ServiceBuilderImpl(final T instance) {
    settings = new ServiceSettings<T>(instance);
  }

  public ServiceBuilderImpl<T> decoratedWith(final Key<? extends ImportDecorator<? super T>> key) {
    settings.setDecorator(Setting.newSetting(key));
    return this;
  }

  public ServiceBuilderImpl<T> decoratedWith(final ImportDecorator<? super T> instance) {
    settings.setDecorator(Setting.<ImportDecorator<? super T>> newSetting(instance));
    return this;
  }

  public ServiceBuilderImpl<T> attributes(final Key<? extends Map<String, ?>> key) {
    settings.setAttributes(Setting.newSetting(key));
    return this;
  }

  public ServiceBuilderImpl<T> attributes(final Map<String, ?> instance) {
    settings.setAttributes(Setting.<Map<String, ?>> newSetting(instance));
    return this;
  }

  public ServiceBuilderImpl<T> filter(final Key<? extends AttributeFilter> key) {
    settings.setFilter(Setting.newSetting(key));
    return this;
  }

  public ServiceBuilderImpl<T> filter(final AttributeFilter instance) {
    settings.setFilter(Setting.newSetting(instance));
    return this;
  }

  public ServiceBuilderImpl<T> in(final Key<? extends ServiceRegistry> key) {
    settings.setRegistry(Setting.newSetting(key));
    return this;
  }

  public ServiceBuilderImpl<T> in(final ServiceRegistry instance) {
    settings.setRegistry(Setting.newSetting(instance));
    return this;
  }

  public ServiceBuilderImpl<T> out(final Key<? extends ServiceScope<? super T>> key) {
    settings.setWatcher(Setting.newSetting(key));
    return this;
  }

  public ServiceBuilderImpl<T> out(final ServiceScope<? super T> instance) {
    settings.setWatcher(Setting.<ServiceScope<? super T>> newSetting(instance));
    return this;
  }

  public ProxyProvider<T> single() {
    return new SingleServiceProvider<T>(settings);
  }

  public ProxyProvider<Iterable<T>> multiple() {
    return new MultipleServiceProvider<T>(settings);
  }

  public ExportProvider<T> export() {
    return new ExportedServiceProvider<T>(settings);
  }
}
