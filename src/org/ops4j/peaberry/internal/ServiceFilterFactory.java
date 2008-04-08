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

package org.ops4j.peaberry.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.ops4j.peaberry.Service;

/**
 * Provide LDAP filters from {@link Service} specifications.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceFilterFactory {

  // utility: instances not allowed
  private ServiceFilterFactory() {}

  /**
   * Extract the expected service type from the member being injected.
   * 
   * @param memberType runtime type of member being injected
   * @return expected service type
   */
  public static Class<?> getServiceType(Type memberType) {

    // service type inside Iterable<T>
    if (expectsSequence(memberType)) {
      memberType = ((ParameterizedType) memberType).getActualTypeArguments()[0];
    }

    // remove remaining generic parameters
    if (memberType instanceof ParameterizedType) {
      memberType = ((ParameterizedType) memberType).getRawType();
    }

    return (Class<?>) memberType;
  }

  /**
   * Convert {@link Service} specification into an LDAP filter.
   * 
   * @param spec annotation details for the injected service
   * @param serviceType runtime type of service being injected
   * @return LDAP filter
   */
  public static String getServiceFilter(Service spec, Class<?> serviceType) {

    /*
     * STEP 1: default specification, just use service type
     */
    if (null == spec) {
      return getServiceTypeFilter(serviceType);
    }

    /*
     * STEP 2: normalise custom filter, apply missing brackets
     */
    String customFilter = spec.value().trim();
    if (customFilter.length() > 0 && !customFilter.startsWith("(")) {
      customFilter = '(' + customFilter + ')';
    }

    /*
     * STEP 3: if custom filter tests the service type then we're done
     */
    if (customFilter.toLowerCase().contains("(objectclass")) {
      return customFilter;
    }

    /*
     * STEP 4: create class filter, AND'ing together clauses
     */
    String classFilter = getInterfaceFilter(spec);
    if (classFilter.length() == 0) {
      classFilter = getServiceTypeFilter(serviceType);
    }

    /*
     * STEP 5: combine custom filter AND class filter into single LDAP filter
     */
    final String comboFilter;
    if (customFilter.length() > 0) {
      comboFilter = "(&" + customFilter + classFilter + ')';
    } else {
      comboFilter = classFilter;
    }

    return comboFilter;
  }

  /**
   * Create LDAP filter to find a service with specific interfaces.
   * 
   * @param spec annotation details for the injected service
   * @return an LDAP filter for the specific interfaces
   */
  private static String getInterfaceFilter(Service spec) {
    StringBuilder interfaceClauses = new StringBuilder();

    final Class<?>[] interfaces = spec.interfaces();
    for (Class<?> i : interfaces) {
      interfaceClauses.append("(objectclass=");
      interfaceClauses.append(i.getName());
      interfaceClauses.append(')');
    }

    if (interfaces.length > 1) {
      // AND together multiple clauses
      interfaceClauses.insert(0, "(&");
      interfaceClauses.append(')');
    }

    return interfaceClauses.toString();
  }

  /**
   * Check to see if service type is hidden inside a sequence like Iterable<T>.
   * 
   * @param type runtime type of member being injected
   * @return true if member expects a sequence of services
   */
  public static boolean expectsSequence(Type type) {
    if (type instanceof ParameterizedType) {
      return Iterable.class.equals(((ParameterizedType) type).getRawType());
    }
    return false;
  }

  /**
   * Create LDAP filter to find the service being injected.
   * 
   * @param serviceType runtime type of service being injected
   * @return an LDAP filter for the injected service
   */
  private static String getServiceTypeFilter(Class<?> serviceType) {
    return "(objectclass=" + serviceType.getName() + ')';
  }
}
