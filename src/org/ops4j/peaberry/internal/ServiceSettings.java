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
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;
import org.ops4j.peaberry.builders.ImportDecorator;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * Maintain state of {@link ServiceBuilderImpl} while the fluent API is used.
 * Also includes a few helpers to simplify the import and export of services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceSettings<T>
    implements Cloneable {

  // initial constant settings
  private final Setting<T> service;
  private final Class<T> clazz;

  // current builder state...
  private Setting<ServiceRegistry> registry = newSetting(Key.get(ServiceRegistry.class));
  private Setting<ImportDecorator<? super T>> decorator = nullSetting();
  private Setting<ServiceScope<? super T>> watcher = nullSetting();
  private Setting<Map<String, ?>> attributes = nullSetting();
  private Setting<AttributeFilter> filter = nullSetting();

  /**
   * Configure service based on binding key.
   */
  @SuppressWarnings("unchecked")
  ServiceSettings(final Key<? extends T> key) {
    service = newSetting(key);
    clazz = (Class) key.getTypeLiteral().getRawType();
  }

  /**
   * Configure service based on explicit instance.
   */
  @SuppressWarnings("unchecked")
  ServiceSettings(final T instance) {
    if (null == instance) {
      service = nullSetting();
      clazz = (Class) Object.class;
    } else {
      service = newSetting(instance);
      clazz = (Class) instance.getClass();
    }
  }

  // setters...

  void setDecorator(final Setting<ImportDecorator<? super T>> decorator) {
    this.decorator = decorator;
  }

  void setAttributes(final Setting<Map<String, ?>> attributes) {
    this.attributes = attributes;
  }

  void setFilter(final Setting<AttributeFilter> filter) {
    this.filter = filter;
  }

  void setWatcher(final Setting<ServiceScope<? super T>> watcher) {
    this.watcher = watcher;
  }

  void setRegistry(final Setting<ServiceRegistry> registry) {
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

  // query methods...

  Class<T> getClazz() {
    return clazz;
  }

  ImportDecorator<? super T> getDecorator(final Injector injector) {
    return decorator.get(injector);
  }

  private AttributeFilter getFilter(final Injector injector) {
    final AttributeFilter attributeFilter = filter.get(injector);
    if (null == attributeFilter) {
      final Map<String, ?> serviceAttributes = attributes.get(injector);
      if (null != serviceAttributes) {

        // no filter configured, so attempt to use attributes as a basic filter
        return new AttributeFilter() {
          public boolean matches(final Map<String, ?> targetAttributes) {
            return targetAttributes.entrySet().contains(serviceAttributes.entrySet());
          }
        };
      }
    }
    return attributeFilter;
  }

  Iterable<Import<T>> getImports(final Injector injector) {
    final ServiceRegistry serviceRegistry = registry.get(injector);
    final AttributeFilter attributeFilter = getFilter(injector);

    // enable outjection, but only if it's going to a different scope
    final ServiceScope<? super T> serviceScope = watcher.get(injector);
    if (null != serviceScope && serviceScope != serviceRegistry) { // NOPMD
      serviceRegistry.watch(clazz, attributeFilter, serviceScope);
    }

    return serviceRegistry.lookup(clazz, attributeFilter);
  }

  private ServiceScope<? super T> getReceivingScope(final Injector injector) {
    final ServiceScope<? super T> serviceScope = watcher.get(injector);
    if (null == serviceScope) {
      return registry.get(injector);
    }
    return serviceScope;
  }

  Export<T> getExport(final Injector injector) {

    // wrap local instance up as an import and push it out to the relevant scope
    final Import<T> _import = new StaticImport<T>(service.get(injector), attributes.get(injector));
    final Export<T> _export = getReceivingScope(injector).add(_import);

    // apply decoration to the incoming aspect, just like with normal imports
    final ImportDecorator<? super T> importDecorator = decorator.get(injector);
    if (null != importDecorator) {
      return new DecoratedExport<T>(_export, importDecorator);
    }

    return _export;
  }
}
