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
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

/**
 * Provide instances of LDAP filters from Service specifications.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceFilterFactory {

  /**
   * Convert OSGiService spec into LDAP filter for the ServiceTracker.
   * 
   * @param spec annotation details for the injected OSGi service
   * @param memberType the type of the member being injected
   * @return an LDAP filter object
   * @throws InvalidSyntaxException
   */
  public static Filter get(Service spec, Type memberType)
      throws InvalidSyntaxException {

    /*
     * STEP 1: default spec, base filter on member type
     */
    if (null == spec) {
      return FrameworkUtil.createFilter(getMemberTypeFilter(memberType));
    }

    /*
     * STEP 2: normalize custom filter, apply missing brackets
     */
    String customFilter = spec.value().trim();
    if (customFilter.length() > 0 && !customFilter.startsWith("(")) {
      customFilter = '(' + customFilter + ')';
    }

    /*
     * STEP 3: if custom filter includes class filter then we're done
     */
    if (customFilter.toLowerCase().contains(
        '(' + Constants.OBJECTCLASS.toLowerCase())) {
      return FrameworkUtil.createFilter(customFilter);
    }

    /*
     * STEP 4: create interface class filter, AND'ing together clauses
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

    return FrameworkUtil.createFilter(comboFilter);
  }

  /**
   * Create LDAP filter to find an OSGi service with specific interfaces.
   * 
   * @param spec annotation details for the injected OSGi service
   * @return an LDAP filter string for the specific interfaces
   */
  private static String getInterfaceFilter(Service spec) {
    StringBuilder interfaceClauses = new StringBuilder();

    final Class<?>[] interfaces = spec.interfaces();
    for (Class<?> i : interfaces) {
      interfaceClauses.append('(' + Constants.OBJECTCLASS + '=');
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
   * Create LDAP filter to find an OSGi service for the member being injected.
   * 
   * @param memberType the type of the member being injected
   * @return an LDAP filter string for the injected member
   */
  private static String getMemberTypeFilter(Type memberType) {

    // multiple service dependency, ie. Iterable<T>
    if (memberType instanceof ParameterizedType) {
      ParameterizedType paramType = (ParameterizedType) memberType;
      if (Iterable.class == paramType.getRawType()) {
        memberType = paramType.getActualTypeArguments()[0];
      }
    }

    // use raw type for other generic services
    if (memberType instanceof ParameterizedType) {
      memberType = ((ParameterizedType) memberType).getRawType();
    }

    final String className = ((Class<?>) memberType).getName();
    return '(' + Constants.OBJECTCLASS + '=' + className + ')';
  }
}
