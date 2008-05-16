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
 * Something that watches services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public interface ServiceWatcher {

  /**
   * LDAP attribute (&quot;service.filter&quot;) set by {@link ServiceWatcher}
   * services to an LDAP filter that matches services they are interested in.
   */
  static final String SERVICE_FILTER = "service.filter";

  /**
   * Ask the watcher to start watching the given service.
   * 
   * @param service service instance
   * @param attributes service attributes
   * 
   * @return handle to watched service, null if the watcher is not interested
   */
  <T> Handle<T> add(T service, Map<String, ?> attributes);

  /**
   * Handle to a watched service.
   */
  public interface Handle<T> {

    /**
     * Get the watched service instance (may be decorated by the watcher).
     * 
     * @return service instance, null if it is no longer being watched
     */
    T get();

    /**
     * Modify the attributes of the watched service.
     * 
     * @param attributes service attributes
     */
    void modify(Map<String, ?> attributes);

    /**
     * Ask the watcher to stop watching this service.
     */
    void remove();
  }
}
