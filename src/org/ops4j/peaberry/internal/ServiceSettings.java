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

import static org.ops4j.peaberry.internal.Setting.nullSetting;

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.builders.ImportDecorator;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceSettings<T>
    implements Cloneable {

  final Setting<T> service;

  private Setting<ImportDecorator<T>> decorator = nullSetting();
  private Setting<Map<String, ?>> attributes = nullSetting();
  private Setting<AttributeFilter> filter = nullSetting();

  private Setting<ServiceRegistry> registry =
      new Setting<ServiceRegistry>(Key.get(ServiceRegistry.class));

  public ServiceSettings(final Key<? extends T> service) {
    this.service = new Setting<T>(service);
  }

  public ServiceSettings(final T service) {
    this.service = new Setting<T>(service);
  }

  public void setDecorator(final Setting<ImportDecorator<T>> decorator) {
    this.decorator = decorator;
  }

  public void setAttributes(final Setting<Map<String, ?>> attributes) {
    this.attributes = attributes;
  }

  public void setFilter(final Setting<AttributeFilter> filter) {
    this.filter = filter;
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
      return null;
    }
  }

  public Class<T> getRawType() {
    return service.getRawType();
  }

  public ImportDecorator<T> getDecorator(final Injector injector) {
    return decorator.get(injector);
  }

  public Iterable<Import<T>> getImports(final Injector injector) {
    return registry.get(injector).lookup(service.getRawType(), filter.get(injector));
  }

  public Export<T> getExport(final Injector injector) {
    final T instance;

    final ImportDecorator<T> d = getDecorator(injector);
    if (d != null) {
      instance = d.decorate(new Import<T>() {
        public T get() {
          return service.get(injector);
        }

        public Map<String, ?> attributes() {
          return null;
        }

        public void unget() {}
      }).get();
    } else {
      instance = service.get(injector);
    }

    return registry.get(injector).export(instance, attributes.get(injector));
  }
}
