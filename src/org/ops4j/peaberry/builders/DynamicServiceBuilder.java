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

/**
 * See the dynamic service EDSL examples at {@link Peaberry}.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public interface DynamicServiceBuilder<T>
    extends FilteredServiceBuilder<T> {

  /**
   * Periodically cache the result of each successful dynamic service lookup.
   * 
   * @param seconds lease period in seconds
   * @return service EDSL builder
   */
  FilteredServiceBuilder<T> leased(int seconds);

  /**
   * Statically cache the result of the first successful dynamic service lookup.
   * 
   * @return service EDSL builder
   */
  FilteredServiceBuilder<T> constant();
}
