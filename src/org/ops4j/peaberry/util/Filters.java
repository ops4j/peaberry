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

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.util.ldap.LdapAttributeFilter;

/**
 * Methods for dealing with service attribute filters.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Filters {

  // instances not allowed
  private Filters() {}

  /**
   * Create a custom attribute filter based on the given LDAP filter string.
   * 
   * @param filter RFC-1960 LDAP filter
   * @return service attribute filter
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc1960.txt">RFC-1960</a>
   */
  public static AttributeFilter ldap(final String filter) {
    return new LdapAttributeFilter(filter);
  }

  /**
   * Create an <i>objectClass</i> attribute filter from the given service API.
   * 
   * @param interfaces service API
   * @return service attribute filter
   */
  public static AttributeFilter objectClass(final Class<?>... interfaces) {
    final StringBuilder filter = new StringBuilder();

    // must AND multiple clauses
    if (interfaces.length > 1) {
      filter.append("(&");
    }

    for (final Class<?> i : interfaces) {
      filter.append('(' + OBJECTCLASS + '=');
      filter.append(i.getName());
      filter.append(')');
    }

    if (interfaces.length > 1) {
      filter.append(')');
    }

    return ldap(filter.toString());
  }

  /**
   * Create a custom attribute filter based on the given service attributes.
   * 
   * @param attributes service attributes
   * @return service attribute filter
   */
  public static AttributeFilter attributes(final Map<String, ?> attributes) {
    return new AttributeFilter() {
      public boolean matches(final Map<String, ?> targetAttributes) {
        return targetAttributes.entrySet().contains(attributes.entrySet());
      }
    };
  }
}
