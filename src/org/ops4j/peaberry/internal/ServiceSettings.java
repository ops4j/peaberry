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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

  // initial constant settings
  private final Setting<T> service;
  private final Class<?> clazz;

  private Setting<ImportDecorator<? super T>> decorator = nullSetting();
  private Setting<ServiceScope<? super T>> watcher = nullSetting();
  private Setting<Map<String, ?>> attributes = nullSetting();
  private Setting<AttributeFilter> filter = nullSetting();

  // default to current binding, which in most cases will be the OSGi registry
  private Setting<ServiceRegistry> registry = newSetting(Key.get(ServiceRegistry.class));

  /**
   * Configure service based on binding key.
   */
  public ServiceSettings(final Key<? extends T> key) {
    this.service = newSetting(key);

    // extract non-generic type information
    Type type = key.getTypeLiteral().getType();
    if (type instanceof ParameterizedType) {
      type = ((ParameterizedType) type).getRawType();
    }

    // fail-safe in case we still can't find the raw type
    clazz = type instanceof Class ? (Class<?>) type : Object.class;
  }

  /**
   * Configure service based on explicit instance.
   */
  public ServiceSettings(final T instance) {
    this.service = newSetting(instance);

    clazz = instance.getClass();
  }

  // setters...

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

  // helper methods...

  @Override
  @SuppressWarnings("unchecked")
  public ServiceSettings<T> clone() {
    try {
      // clone all settings to preserve state
      return (ServiceSettings<T>) super.clone();
    } catch (final CloneNotSupportedException e) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  public Class<T> clazz() {
    return (Class<T>) clazz;
  }

  public T instance(final Injector injector) {
    return service.get(injector);
  }

  public ImportDecorator<? super T> decorator(final Injector injector) {
    return decorator.get(injector);
  }

  public Map<String, ?> attributes(final Injector injector) {
    return attributes.get(injector);
  }

  private AttributeFilter filter(final Injector injector) {
    final AttributeFilter serviceFilter = filter.get(injector);
    if (null == serviceFilter) {
      final Map<String, ?> serviceAttributes = attributes.get(injector);
      if (null != serviceAttributes) {

        // use attributes as a filter
        return new AttributeFilter() {
          public boolean matches(final Map<String, ?> targetAttributes) {
            return targetAttributes.entrySet().contains(serviceAttributes.entrySet());
          }
        };
      }
    }
    return serviceFilter;
  }

  public ServiceScope<? super T> watcher(final Injector injector) {
    final ServiceScope<? super T> serviceScope = watcher.get(injector);
    if (null == serviceScope) {
      // use the registry as a scope
      return registry.get(injector);
    }
    return serviceScope;
  }

  public Iterable<Import<T>> imports(final Injector injector) {
    return registry.get(injector).lookup(clazz(), filter(injector));
  }
}
