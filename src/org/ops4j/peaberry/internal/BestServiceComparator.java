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

import static org.osgi.framework.Constants.SERVICE_ID;
import static org.osgi.framework.Constants.SERVICE_RANKING;

import java.io.Serializable;
import java.util.Comparator;

import org.osgi.framework.ServiceReference;

/**
 * OSGi service {@link Comparator} that puts <i>best</i> services first, using
 * the definition of <i>best</i> service from the OSGi R4 core specification.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class BestServiceComparator
    implements Comparator<ServiceReference>, Serializable {

  private static final long serialVersionUID = 1L;

  public int compare(final ServiceReference lhs, final ServiceReference rhs) {

    final long lhsId = getNumber(lhs, SERVICE_ID);
    final long rhsId = getNumber(rhs, SERVICE_ID);

    // /CLOVER:OFF
    if (lhsId == rhsId) {
      return 0;
    } // /CLOVER:ON

    final long lhsRanking = getNumber(lhs, SERVICE_RANKING);
    final long rhsRanking = getNumber(rhs, SERVICE_RANKING);

    if (lhsRanking == rhsRanking) {
      // /CLOVER:OFF
      return lhsId < rhsId ? -1 : 1;
      // /CLOVER:ON
    }

    return lhsRanking < rhsRanking ? 1 : -1;
  }

  private static long getNumber(final ServiceReference ref, final String key) {
    final Object value = ref.getProperty(key);
    // /CLOVER:OFF
    if (value instanceof Number) {
      // /CLOVER:ON
      return ((Number) value).longValue();
    }
    // /CLOVER:OFF
    return 0L;
  }
}
