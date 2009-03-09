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
 * Handle to a service instance imported from a {@link ServiceRegistry}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface Import<T> {

  /**
   * Start using the imported service instance.
   * 
   * @return service instance
   * 
   * @throws ServiceUnavailableException if the service is unavailable
   */
  T get();

  /**
   * Get the attributes associated with the service.
   * 
   * @return current attribute map
   */
  Map<String, ?> attributes();

  /**
   * Stop using the imported service instance.
   */
  void unget();

  /**
   * Is the imported service instance available?
   * 
   * @return true if the service is available, otherwise false
   * 
   * @since 1.1
   */
  boolean available();
}
