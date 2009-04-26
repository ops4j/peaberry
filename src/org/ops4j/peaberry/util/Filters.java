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
 * Methods for dealing with {@link AttributeFilter}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Filters {

  // instances not allowed
  private Filters() {}

  /**
   * Create an {@link AttributeFilter} based on the given LDAP filter string.
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
   * Create an <i>objectClass</i> {@link AttributeFilter} from the given API.
   * 
   * @param interfaces service API
   * @return service attribute filter
   */
  public static AttributeFilter objectClass(final Class<?>... interfaces) {
    final StringBuilder filter = new StringBuilder();
    int numClauses = 0;

    for (final Class<?> i : interfaces) {
      if (null != i && Object.class != i) { // NOPMD
        filter.append('(' + OBJECTCLASS + '=').append(i.getName()).append(')');
        numClauses++;
      }
    }

    if (0 == numClauses) {
      return null;
    } else if (1 < numClauses) {
      filter.insert(0, "(&").append(')');
    }

    return new LdapAttributeFilter(filter.toString());
  }

  /**
   * Create an {@link AttributeFilter} based on the given service attributes.
   * 
   * @param sampleAttributes sample attributes
   * @return sample attribute filter
   */
  public static AttributeFilter attributes(final Map<String, ?> sampleAttributes) {
    return new AttributeFilter() {
      public boolean matches(final Map<String, ?> attributes) {
        return null != attributes && attributes.entrySet().containsAll(sampleAttributes.entrySet());
      }
    };
  }
}
