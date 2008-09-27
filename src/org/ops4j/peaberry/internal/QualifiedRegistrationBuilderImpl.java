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
 * Default {@code QualifiedRegistrationBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class QualifiedRegistrationBuilderImpl<T>
    implements QualifiedRegistrationBuilder<T> {

  private final RegistrationSettings<T> settings;

  public QualifiedRegistrationBuilderImpl(final Key<? extends T> key) {
    settings = new RegistrationSettings<T>(new Setting<T>(key));
  }

  public QualifiedRegistrationBuilderImpl(final T instance) {
    settings = new RegistrationSettings<T>(new Setting<T>(instance));
  }

  public ScopedRegistrationBuilder<T> attributes(final Key<? extends Map<String, ?>> key) {
    settings.setAttributes(new Setting<Map<String, ?>>(key));
    return this;
  }

  public ScopedRegistrationBuilder<T> attributes(final Map<String, ?> instance) {
    settings.setAttributes(new Setting<Map<String, ?>>(instance));
    return this;
  }

  public RegistrationProxyBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    settings.setRegistry(new Setting<ServiceRegistry>(key));
    return this;
  }

  public RegistrationProxyBuilder<T> in(final ServiceRegistry instance) {
    settings.setRegistry(new Setting<ServiceRegistry>(instance));
    return this;
  }

  public Provider<Export<T>> export() {
    final RegistrationSettings<T> registration = settings.clone();
    return new Provider<Export<T>>() {

      @Inject
      Injector injector;

      public Export<T> get() {
        return registration.export(injector);
      }
    };
  }
}
