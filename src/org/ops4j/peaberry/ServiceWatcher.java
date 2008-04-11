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
 * Something that watches for services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public interface ServiceWatcher {

  /**
   * Service property (&quot;service.filter&quot;) set by {@link ServiceWatcher}
   * services to an LDAP filter that matches services they are interested in.
   */
  static final String SERVICE_FILTER = "service.filter";

  /**
   * Ask the watcher to start watching the given service.
   * 
   * @param service service instance
   * @param properties service properties
   * 
   * @return handle to watched service, null if the watcher is not interested
   */
  <T> Handle<T> add(T service, Map<?, ?> properties);

  /**
   * Handle to a watched service.
   */
  interface Handle<T> {

    /**
     * Modify the properties of the watched service.
     * 
     * @param properties service properties
     */
    void modify(Map<?, ?> properties);

    /**
     * Remove the service from being watched.
     */
    void remove();
  }
}
