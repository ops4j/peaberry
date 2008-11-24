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

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Peaberry;

import com.google.inject.Key;

/**
 * See {@link Peaberry} for examples of the dynamic service builder API.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface QualifiedServiceBuilder<T>
    extends InjectedServiceBuilder<T> {

  /**
   * Apply the given attributes to the dynamic service.
   * 
   * @param key service attributes key
   * @return dynamic service builder
   */
  InjectedServiceBuilder<T> attributes(Key<? extends Map<String, ?>> key);

  /**
   * Apply the given attributes to the dynamic service.
   * 
   * @param instance service attributes
   * @return dynamic service builder
   */
  InjectedServiceBuilder<T> attributes(Map<String, ?> instance);

  /**
   * Apply the given filter to the dynamic service.
   * 
   * @param key attribute filter key
   * @return dynamic service builder
   */
  InjectedServiceBuilder<T> filter(Key<? extends AttributeFilter> key);

  /**
   * Apply the given filter to the dynamic service.
   * 
   * @param instance attribute filter
   * @return dynamic service builder
   */
  InjectedServiceBuilder<T> filter(AttributeFilter instance);
}
