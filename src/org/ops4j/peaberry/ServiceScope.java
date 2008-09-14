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
 * <i>Scopes can be monitored by exporting your own custom scope as a service.
 * When a service is exported to a scope, it is also exported to any contained
 * {@code ServiceScope} services who have {@code scope.filter}s that match the
 * service being exported.</i>
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface ServiceScope {

  /**
   * {@code scope.filter} service attribute, an {@code AttributeFilter} that
   * defines which services should be exported to the associated scope service.
   */
  static final String SCOPE_FILTER = "scope.filter";

  /**
   * Export the given service to this scope, and any matching scope services.
   * 
   * @param service service instance
   * @param attributes service attributes
   * 
   * @return exported service handle, null if this scope is not interested
   */
  <S, T extends S> Export<S> export(T service, Map<String, ?> attributes);
}
