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

import static org.ops4j.peaberry.internal.Setting.newSetting;
import static org.ops4j.peaberry.internal.Setting.nullSetting;

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;
import org.ops4j.peaberry.builders.ImportDecorator;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * Maintain state of {@code ServiceBuilderImpl} while fluent API is used.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceSettings<T>
    implements Cloneable {

  private final Setting<T> service;

  private Setting<ServiceRegistry> registry = newSetting(Key.get(ServiceRegistry.class));

  private Setting<ImportDecorator<? super T>> decorator = nullSetting();
  private Setting<ServiceScope<? super T>> watcher = nullSetting();
  private Setting<Map<String, ?>> attributes = nullSetting();
  private Setting<AttributeFilter> filter = nullSetting();

  public ServiceSettings(final Key<? extends T> service) {
    this.service = newSetting(service);
  }

  public ServiceSettings(final T service) {
    this.service = newSetting(service);
  }

  public void setDecorator(final Setting<ImportDecorator<? super T>> decorator) {
    this.decorator = decorator;
  }

  public void setAttributes(final Setting<Map<String, ?>> attributes) {
    this.attributes = attributes;
  }

  public void setFilter(final Setting<AttributeFilter> filter) {
    this.filter = filter;
  }

  public void setWatcher(final Setting<ServiceScope<? super T>> watcher) {
    this.watcher = watcher;
  }

  public void setRegistry(final Setting<ServiceRegistry> registry) {
    this.registry = registry;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ServiceSettings<T> clone() {
    try {
      return (ServiceSettings<T>) super.clone();
    } catch (final CloneNotSupportedException e) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  public Class<T> clazz() {
    return (Class<T>) service.getRawType();
  }

  public Iterable<Import<T>> imports(final Injector injector) {
    return registry.get(injector).lookup(clazz(), filter.get(injector));
  }

  public ImportDecorator<? super T> decorator(final Injector injector) {
    return decorator.get(injector);
  }

  public ServiceScope<? super T> watcher(final Injector injector) {
    final ServiceScope<? super T> scope = watcher.get(injector);
    if (null == scope) {
      return registry.get(injector);
    }
    return scope;
  }

  public T instance(final Injector injector) {
    return service.get(injector);
  }

  public Map<String, ?> attributes(final Injector injector) {
    return attributes.get(injector);
  }
}
