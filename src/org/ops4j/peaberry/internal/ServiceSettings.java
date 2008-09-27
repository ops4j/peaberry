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
import static org.ops4j.peaberry.internal.Setting.nullSetting;

import org.ops4j.peaberry.AttributeFilter;
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

  private final Class<? extends T> clazz;
  private Setting<ImportDecorator<? super T>> decorator;
  private Setting<AttributeFilter> filter;
  private Setting<ServiceRegistry> registry;
  private boolean isDirect;

  public ServiceSettings(final Class<? extends T> clazz) {
    this.clazz = clazz;
    this.decorator = nullSetting();
    this.filter = nullSetting();
    this.registry = new Setting<ServiceRegistry>(Key.get(ServiceRegistry.class));
  }

  public void setDecorator(final Setting<ImportDecorator<? super T>> decorator) {
    this.decorator = decorator;
  }

  public void setFilter(final Setting<AttributeFilter> filter) {
    this.filter = filter;
  }

  public void setRegistry(final Setting<ServiceRegistry> registry) {
    this.registry = registry;
  }

  public void setDirect() {
    this.isDirect = true;
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

  private Iterable<Import<T>> getHandles(final Injector injector) {
    return registry.get(injector).lookup(clazz, filter.get(injector));
  }

  public T single(final Injector injector) {
    if (isDirect) {
      return directService(getHandles(injector), decorator.get(injector));
    }
    return serviceProxy(clazz, getHandles(injector), decorator.get(injector));
  }

  public Iterable<T> multiple(final Injector injector) {
    if (isDirect) {
      return directServices(getHandles(injector), decorator.get(injector));
    }
    return serviceProxies(clazz, getHandles(injector), decorator.get(injector));
  }
}
