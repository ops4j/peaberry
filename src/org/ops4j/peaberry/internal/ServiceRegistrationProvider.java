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

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.internal.QualifiedRegistrationBuilderImpl.Configuration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * {@code Provider} implementation that provides exported service registrations.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ServiceRegistrationProvider<T>
    implements Provider<Export<T>> {

  @Inject
  Injector injector; // field-injection

  // snapshot of builder configuration
  private final Key<? extends T> serviceKey;
  private final Map<String, ?> attributes;
  private final Key<? extends ServiceRegistry> registryKey;

  public ServiceRegistrationProvider(final Key<? extends T> key, final Configuration<T> config) {
    this.serviceKey = key;
    this.attributes = config.attributes;
    this.registryKey = config.registryKey;
  }

  private ServiceRegistry getRegistry() {
    if (null == registryKey) {
      return injector.getInstance(ServiceRegistry.class);
    }
    return injector.getInstance(registryKey);
  }

  public Export<T> get() {
    return getRegistry().export(injector.getInstance(serviceKey), attributes);
  }
}
