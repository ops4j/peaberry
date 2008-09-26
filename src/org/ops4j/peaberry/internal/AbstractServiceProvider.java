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
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.internal.DecoratedServiceBuilderImpl.Configuration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Partial {@code Provider} implementation that provides imported services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
abstract class AbstractServiceProvider<T, X>
    implements Provider<X> {

  @Inject
  Injector injector; // field-injection

  // snapshot of builder configuration
  final Class<? extends T> serviceClazz;
  final AttributeFilter attributeFilter;
  final boolean isDirect;

  private final Key<? extends ImportDecorator<? super T>> decoratorKey;
  private final Key<? extends ServiceRegistry> registryKey;

  public AbstractServiceProvider(final Class<? extends T> clazz, final Configuration<T> config) {
    this.serviceClazz = clazz;
    this.attributeFilter = config.attributeFilter;
    this.isDirect = config.isDirect;
    this.decoratorKey = config.decoratorKey;
    this.registryKey = config.registryKey;
  }

  private ServiceRegistry getRegistry() {
    if (null == registryKey) {
      return injector.getInstance(ServiceRegistry.class);
    }
    return injector.getInstance(registryKey);
  }

  Iterable<Import<T>> getServices() {
    return getRegistry().lookup(serviceClazz, attributeFilter);
  }

  ImportDecorator<? super T> getDecorator() {
    return null == decoratorKey ? null : injector.getInstance(decoratorKey);
  }
}
