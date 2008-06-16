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

/**
 * Collection of utility methods for dealing with service filters.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class Filter {

  // instances not allowed
  private Filter() {}

  /**
   * Convert service API to the appropriate OBJECTCLASS filter.
   * 
   * @param interfaces service API
   * @return RFC-1960 LDAP filter
   */
  public static String objectclass(final Class<?>... interfaces) {
    final StringBuilder filter = new StringBuilder();

    for (final Class<?> i : interfaces) {
      filter.append("(OBJECTCLASS=");
      filter.append(i.getName());
      filter.append(')');
    }

    // AND clauses together
    if (interfaces.length > 1) {
      filter.insert(0, "(&");
      filter.append(')');
    }

    return filter.toString();
  }
}
