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

/**
 * Simple abstraction of a dynamic service registry.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public interface ServiceRegistry
    extends ServiceWatcher {

  /**
   * Lookup service instances from registry, constrained by LDAP filter.
   * 
   * @param clazz expected service class
   * @param filter RFC-1960 LDAP filter
   * 
   * @return sequence of service instances
   */
  <T> Iterable<T> lookup(Class<? extends T> clazz, String filter);
}
