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

package org.ops4j.peaberry.util;

import static org.osgi.framework.Constants.OBJECTCLASS;

/**
 * Collection of utility methods for dealing with service filters.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Filters {

  // instances not allowed
  private Filters() {}

  /**
   * Convert service API to the appropriate <i>objectClass</i> filter.
   * 
   * @param interfaces service API
   * @return RFC-1960 LDAP filter
   */
  public static String objectClass(final Class<?>... interfaces) {
    final StringBuilder filter = new StringBuilder();

    for (final Class<?> i : interfaces) {
      filter.append('(' + OBJECTCLASS + '=' + i.getName() + ')');
    }

    // must AND multiple clauses
    if (interfaces.length > 1) {
      return "(&" + filter + ')';
    }

    return filter.toString();
  }
}
