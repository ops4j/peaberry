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
 * Provide LDAP queries from {@literal @}Service specifications.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceQueryFactory {

  /**
   * Convert {@literal @}Service specification into an LDAP query.
   * 
   * @param spec annotation details for the injected service
   * @param memberType runtime type of member being injected
   * @return LDAP query string
   */
  public static String get(Service spec, Type memberType) {

    /*
     * STEP 1: default spec, query on member type
     */
    if (null == spec) {
      return getMemberTypeQuery(memberType);
    }

    /*
     * STEP 2: normalize custom query, apply missing brackets
     */
    String customQuery = spec.value().trim();
    if (customQuery.length() > 0 && !customQuery.startsWith("(")) {
      customQuery = '(' + customQuery + ')';
    }

    /*
     * STEP 3: if custom query tests the member type then we're done
     */
    if (customQuery.toLowerCase().contains("(objectclass")) {
      return customQuery;
    }

    /*
     * STEP 4: create interface type query, AND'ing together clauses
     */
    String classQuery = getInterfaceQuery(spec);
    if (classQuery.length() == 0) {
      classQuery = getMemberTypeQuery(memberType);
    }

    /*
     * STEP 5: combine custom query AND class query into single LDAP query
     */
    final String comboQuery;
    if (customQuery.length() > 0) {
      comboQuery = "(&" + customQuery + classQuery + ')';
    } else {
      comboQuery = classQuery;
    }

    return comboQuery;
  }

  /**
   * Create LDAP query to find a service with specific interfaces.
   * 
   * @param spec annotation details for the injected service
   * @return an LDAP query string for the specific interfaces
   */
  private static String getInterfaceQuery(Service spec) {
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
   * Create LDAP query to find a service for the member being injected.
   * 
   * @param memberType the type of the member being injected
   * @return an LDAP query string for the injected member
   */
  private static String getMemberTypeQuery(Type memberType) {

    // multiple service dependency, ie. Iterable<T>
    if (memberType.toString().startsWith("java.lang.Iterable<")) {
      memberType = ((ParameterizedType) memberType).getActualTypeArguments()[0];
    }

    // use raw type when finding generic services
    if (memberType instanceof ParameterizedType) {
      memberType = ((ParameterizedType) memberType).getRawType();
    }

    final String className = ((Class<?>) memberType).getName();
    return "(objectclass=" + className + ')';
  }
}
