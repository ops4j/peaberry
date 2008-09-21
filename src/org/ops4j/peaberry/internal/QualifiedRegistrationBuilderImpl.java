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

import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Default {@code QualifiedRegistrationBuilder} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class QualifiedRegistrationBuilderImpl<T>
    implements QualifiedRegistrationBuilder<T> {

  private final Key<? extends T> serviceKey;
  private final Configuration<T> config;

  // mutable builder settings
  static class Configuration<T> {
    Map<String, ?> attributes;
    Key<? extends ServiceRegistry> registryKey;
  }

  public QualifiedRegistrationBuilderImpl(final Key<? extends T> key) {
    config = new Configuration<T>();
    serviceKey = key;
  }

  public ScopedRegistrationBuilder<T> attributes(final Map<String, ?> attributes) {
    config.attributes = attributes;
    return this;
  }

  public RegistrationProxyBuilder<T> in(final Key<? extends ServiceRegistry> key) {
    config.registryKey = key;
    return this;
  }

  public Provider<Export<T>> export() {
    return new ServiceRegistrationProvider<T>(serviceKey, config);
  }
}
