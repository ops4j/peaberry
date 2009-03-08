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

import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;
import static org.osgi.framework.Constants.OBJECTCLASS;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

/**
 * Methods for dealing with service attributes.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Attributes {

  // instances not allowed
  private Attributes() {}

  /**
   * Convert the given service properties into a service attribute map.
   * 
   * @param properties service properties
   * @return service attributes
   * 
   * @throws IllegalArgumentException if there are any non-String keys
   */
  public static Map<String, ?> properties(final Properties properties) {
    final Map<String, Object> attributes = new HashMap<String, Object>(2 * properties.size());

    /*
     * Sigh, Properties is a really messed-up class... in Java5 there is only
     * one method that returns all the keys _including default keys_ and that
     * throws a ClassCastException if there happen to be any non-String keys.
     * (Java6 adds stringPropertyNames, but we're currently targeting Java5)
     */
    try {
      for (final Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
        final String key = (String) e.nextElement();
        attributes.put(key, properties.getProperty(key));
      }
    } catch (final ClassCastException e) {
      throw new IllegalArgumentException("Property map contains non-String key", e);
    }

    // now add non-String values that have String keys
    for (final Entry<?, ?> entry : properties.entrySet()) {
      attributes.put(entry.getKey().toString(), entry.getValue());
    }

    return unmodifiableMap(attributes);
  }

  /**
   * Convert the given LDAP distinguished names into a service attribute map.
   * 
   * @param names distinguished names
   * @return service attributes
   * 
   * @throws IllegalArgumentException if there are any invalid names
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC-2253</a>
   */
  public static Map<String, ?> names(final String... names) {
    final Map<String, Object> attributes = new HashMap<String, Object>(2 * names.length);

    for (final String n : names) {
      try {
        for (final Rdn rdn : new LdapName(n).getRdns()) { // NOPMD
          attributes.put(rdn.getType(), rdn.getValue());
        }
      } catch (final InvalidNameException e) {
        throw new IllegalArgumentException("Bad LDAP name: " + n, e);
      }
    }

    return unmodifiableMap(attributes);
  }

  /**
   * Create an <i>objectClass</i> service attribute from the given API.
   * 
   * @param interfaces service API
   * @return service attributes
   */
  public static Map<String, ?> objectClass(final Class<?>... interfaces) {
    final String[] objectclass = new String[interfaces.length];

    for (int i = 0; i < interfaces.length; i++) {
      objectclass[i] = interfaces[i].getName();
    }

    return singletonMap(OBJECTCLASS, objectclass);
  }

  /**
   * Create a union of all the services attributes passed into this method.
   * 
   * @param attributes collection of service attributes
   * @return union of the given service attributes
   * 
   * @since 1.1
   */
  public static Map<String, ?> union(@SuppressWarnings("unchecked") final Map... attributes) {
    final Map<String, Object> unionMap = new HashMap<String, Object>();

    for (final Map<String, ?> a : attributes) {
      if (null != a) {
        unionMap.putAll(a);
      }
    }

    return unmodifiableMap(unionMap);
  }
}
