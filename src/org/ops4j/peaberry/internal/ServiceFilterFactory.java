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
   * Convert {@link Service} specification into an LDAP filter.
   * 
   * @param spec annotation details for the injected service
   * @param serviceType runtime type of service being injected
   * 
   * @return LDAP filter
   */
  public static String getServiceFilter(final Service spec,
      final Class<?> serviceType) {

    /*
     * STEP 1: default specification, just use service type
     */
    if (null == spec) {
      return getServiceTypeFilter(serviceType);
    }

    /*
     * STEP 2: normalise custom filter, apply missing brackets
     */
    String customFilter = spec.filter().trim();
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
   * 
   * @return an LDAP filter for the specific interfaces
   */
  private static String getInterfaceFilter(final Service spec) {
    final StringBuilder interfaceClauses = new StringBuilder();

    final Class<?>[] interfaces = spec.interfaces();
    for (final Class<?> i : interfaces) {
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
   * Create LDAP filter to find the service being injected.
   * 
   * @param serviceType runtime type of service being injected
   * 
   * @return an LDAP filter for the injected service
   */
  private static String getServiceTypeFilter(final Class<?> serviceType) {
    return "(objectclass=" + serviceType.getName() + ')';
  }
}
