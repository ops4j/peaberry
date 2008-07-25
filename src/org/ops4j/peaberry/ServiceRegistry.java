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
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface ServiceRegistry
    extends ServiceScope {

  /**
   * Lookup services from the registry, constrained by the given LDAP filter.
   * 
   * @param clazz expected service interface
   * @param filter RFC-1960 LDAP filter
   * 
   * @return sequence of imported service handles
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc1960.txt">RFC-1960</a>
   */
  <T> Iterable<Import<T>> lookup(Class<? extends T> clazz, String filter);
}
