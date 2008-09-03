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
 * A write-only collection of exported services with associated attributes.
 * <p>
 * Service scopes can be nested - when a service is exported to a scope, it is
 * automatically exported to any contained {@code ServiceScope} instances that
 * have {@code scope.filter} attributes that match the exported service.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface ServiceScope {

  /**
   * {@code scope.filter} attribute, an RFC-1960 LDAP filter that defines which
   * services can be exported to the associated {@code ServiceScope} instance.
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc1960.txt">RFC-1960< /a>
   */
  static final String SCOPE_FILTER = "scope.filter";

  /**
   * Export the given service to this scope with the associated attributes.
   * 
   * @param service service instance
   * @param attributes service attributes
   * 
   * @return exported service handle, null if the scope is not interested
   */
  <S, T extends S> Export<S> export(T service, Map<String, ?> attributes);
}
