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

package org.ops4j.peaberry;

import java.util.Map;

/**
 * Handle to a service instance exported to a {@link ServiceScope}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface Export<T>
    extends Import<T> {

  /**
   * Replace the exported service with the given instance.
   * 
   * @param instance service instance
   */
  void put(T instance);

  /**
   * Update the attributes associated with the exported service.
   * 
   * @param attributes service attributes
   */
  void attributes(Map<String, ?> attributes);

  /**
   * Remove the exported service from the {@link ServiceScope}.
   */
  void unput();
}
