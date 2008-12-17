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
import org.ops4j.peaberry.ServiceScope;

import com.google.inject.Key;

/**
 * See {@link Peaberry} for examples of the dynamic service builder API.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface OutjectedServiceBuilder<T>
    extends ServiceBuilder<T> {

  /**
   * Outject the (undecorated) dynamic service to the given scope.
   * 
   * @param key service scope key
   * @return dynamic service builder
   */
  ServiceBuilder<T> out(Key<? extends ServiceScope<? super T>> key);

  /**
   * Outject the (undecorated) dynamic service to the given scope.
   * 
   * @param instance service scope
   * @return dynamic service builder
   */
  ServiceBuilder<T> out(ServiceScope<? super T> instance);
}
