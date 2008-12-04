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

package org.ops4j.peaberry.builders;

import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.ServiceRegistry;

import com.google.inject.Key;

/**
 * See {@link Peaberry} for examples of the dynamic service builder API.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface InjectedServiceBuilder<T>
    extends OutjectedServiceBuilder<T> {

  /**
   * Inject the dynamic service from a specific registry.
   * 
   * @param key service registry key
   * @return dynamic service builder
   */
  OutjectedServiceBuilder<T> in(Key<? extends ServiceRegistry> key);

  /**
   * Inject the dynamic service from a specific registry.
   * 
   * @param instance service registry
   * @return dynamic service builder
   */
  OutjectedServiceBuilder<T> in(ServiceRegistry instance);
}
