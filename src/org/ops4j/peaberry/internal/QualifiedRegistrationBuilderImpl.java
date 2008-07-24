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
import org.ops4j.peaberry.builders.QualifiedRegistrationBuilder;
import org.ops4j.peaberry.builders.RegistrationProxyBuilder;
import org.ops4j.peaberry.builders.ScopedRegistrationBuilder;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Default {@link QualifiedRegistrationBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class QualifiedRegistrationBuilderImpl<T>
    implements QualifiedRegistrationBuilder<T> {

  // primary configuration item
  final Key<? extends T> implementationKey;

  // default configuration
  Map<String, ?> attributes = null;

  // custom configuration keys
  Key<? extends ServiceRegistry> registryKey = null;

  public QualifiedRegistrationBuilderImpl(final Key<? extends T> key) {
    this.implementationKey = key;
  }

  public ScopedRegistrationBuilder<T> attributes(final Map<String, ?> customAttributes) {
    attributes = customAttributes;
    return this;
  }

  public RegistrationProxyBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    registryKey = key;
    return this;
  }

  public Provider<Export<T>> export() {
    return new Provider<Export<T>>() {

      @Inject
      Injector injector;

      public Export<T> get() {

        // time to lookup the actual implementation bindings
        final ServiceRegistry registry = getRegistry(injector);
        final T serviceImpl = injector.getInstance(implementationKey);

        // register implementation with service registry
        return registry.export(serviceImpl, attributes);
      }
    };
  }

  ServiceRegistry getRegistry(final Injector injector) {
    if (registryKey != null) {
      return injector.getInstance(registryKey);
    }
    return injector.getInstance(ServiceRegistry.class);
  }
}
