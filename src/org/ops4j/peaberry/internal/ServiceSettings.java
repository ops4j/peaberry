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
      // /CLOVER:OFF - belts and braces
      return this;
      // /CLOVER:ON
    }
  }

  // query methods...

  Class<T> getClazz() {
    return clazz;
  }

  ImportDecorator<? super T> getDecorator(final Injector injector) {
    return decorator.get(injector);
  }

  // simple filter based on sample attributes
  private static class SampleAttributeFilter
      implements AttributeFilter {

    private final Map<String, ?> attributes;

    public SampleAttributeFilter(final Map<String, ?> attributes) {
      this.attributes = attributes;
    }

    public boolean matches(final Map<String, ?> targetAttributes) {
      return targetAttributes.entrySet().containsAll(attributes.entrySet());
    }

    @Override
    public boolean equals(final Object rhs) {
      if (rhs instanceof SampleAttributeFilter) {
        return attributes.equals(((SampleAttributeFilter) rhs).attributes);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return attributes.hashCode();
    }
  }

  private AttributeFilter getFilter(final Injector injector) {
    final AttributeFilter attributeFilter = filter.get(injector);
    if (null == attributeFilter) {
      final Map<String, ?> serviceAttributes = attributes.get(injector);
      if (null != serviceAttributes) {
        return new SampleAttributeFilter(serviceAttributes);
      }
    }
    return attributeFilter;
  }

  Iterable<Import<T>> getImports(final Injector injector, final boolean isConcurrent) {
    final ServiceRegistry serviceRegistry = registry.get(injector);
    final AttributeFilter attributeFilter = getFilter(injector);

    final Iterable<Import<T>> imports = serviceRegistry.lookup(clazz, attributeFilter);

    // enable outjection, but only if it's going to a different scope
    ServiceScope<? super T> serviceScope = watcher.get(injector);
    if (null != serviceScope && serviceScope != serviceRegistry) { // NOPMD

      final ImportDecorator<? super T> scopeDecorator = decorator.get(injector);
      if (null != scopeDecorator) {
        // decorate the scope if necessary, to support decorated watching
        serviceScope = new DecoratedServiceScope<T>(scopeDecorator, serviceScope);
      }

      if (isConcurrent) {
        // apply concurrent behaviour to scope when watching single services
        serviceScope = new ConcurrentServiceScope<T>(imports, serviceScope);
      }

      serviceRegistry.watch(clazz, attributeFilter, serviceScope);
    }

    return imports;
  }

  Export<T> getExport(final Injector injector) {

    // watcher can be null, but registry setting will be non-null
    ServiceScope<? super T> serviceScope = watcher.get(injector);
    if (null == serviceScope) {
      serviceScope = registry.get(injector);
    }

    // decorate the scope if necessary, to support decorated exports
    final ImportDecorator<? super T> scopeDecorator = decorator.get(injector);
    if (null != scopeDecorator) {
      serviceScope = new DecoratedServiceScope<T>(scopeDecorator, serviceScope);
    }

    return serviceScope.add(new StaticImport<T>(service.get(injector), attributes.get(injector)));
  }
}
