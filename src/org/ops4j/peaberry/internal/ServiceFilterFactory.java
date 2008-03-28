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
 * Provide LDAP filters from {@literal @}Service specifications.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceFilterFactory {

  private ServiceFilterFactory() {
    // don't allow instances of helper class
  }

  /**
   * Convert {@literal @}Service specification into an LDAP filter.
   * 
   * @param spec annotation details for the injected service
   * @param memberType runtime type of member being injected
   * @return LDAP filter
   */
  public static String getServiceFilter(Service spec, Type memberType) {

    /*
     * STEP 1: default specification, just use member type
     */
    if (null == spec) {
      return getMemberTypeFilter(memberType);
    }

    /*
     * STEP 2: normalise custom filter, apply missing brackets
     */
    String customFilter = spec.value().trim();
    if (customFilter.length() > 0 && !customFilter.startsWith("(")) {
      customFilter = '(' + customFilter + ')';
    }

    /*
     * STEP 3: if custom filter tests the member type then we're done
     */
    if (customFilter.toLowerCase().contains("(objectclass")) {
      return customFilter;
    }

    /*
     * STEP 4: create interface type filter, AND'ing together clauses
     */
    String classFilter = getInterfaceFilter(spec);
    if (classFilter.length() == 0) {
      classFilter = getMemberTypeFilter(memberType);
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
   * @param memberType runtime type of member being injected
   * @return true if member expects a sequence of services
   */
  public static boolean expectsSequence(Type memberType) {
    return memberType.toString().startsWith("java.lang.Iterable");
  }

  /**
   * @param memberType runtime type of member being injected
   * @return expected service type
   */
  public static Class<?> getServiceType(Type memberType) {

    // extract the actual service type
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
   * Create LDAP filter to find a service for the member being injected.
   * 
   * @param memberType runtime type of member being injected
   * @return an LDAP filter for the injected member
   */
  private static String getMemberTypeFilter(Type memberType) {
    return "(objectclass=" + getServiceType(memberType).getName() + ')';
  }
}
