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

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceRegistry;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class RegistrationSettings<T>
    implements Cloneable {

  private final Setting<T> implementation;
  private Setting<Map<String, ?>> attributes;
  private Setting<ServiceRegistry> registry;

  public RegistrationSettings(final Setting<T> implementation) {
    this.implementation = implementation;
    this.attributes = nullSetting();
    this.registry = new Setting<ServiceRegistry>(Key.get(ServiceRegistry.class));
  }

  public void setAttributes(final Setting<Map<String, ?>> attributes) {
    this.attributes = attributes;
  }

  public void setRegistry(final Setting<ServiceRegistry> registry) {
    this.registry = registry;
  }

  @Override
  @SuppressWarnings("unchecked")
  public RegistrationSettings<T> clone() {
    try {
      return (RegistrationSettings<T>) super.clone();
    } catch (final CloneNotSupportedException e) {
      return null;
    }
  }

  public Export<T> export(final Injector injector) {
    return registry.get(injector).export(implementation.get(injector), attributes.get(injector));
  }
}
