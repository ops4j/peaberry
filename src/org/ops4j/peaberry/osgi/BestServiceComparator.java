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

package org.ops4j.peaberry.osgi;

import static org.osgi.framework.Constants.SERVICE_ID;
import static org.osgi.framework.Constants.SERVICE_RANKING;

import java.io.Serializable;
import java.util.Comparator;

import org.osgi.framework.ServiceReference;

/**
 * OSGi service {@code Comparator} that puts <i>best</i> services first, using
 * the definition of <i>best</i> service from the OSGi R4 core specification.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class BestServiceComparator
    implements Comparator<ServiceReference>, Serializable {

  private static final long serialVersionUID = 1L;

  public int compare(final ServiceReference lhs, final ServiceReference rhs) {

    final long lhsId = getId(lhs);
    final long rhsId = getId(rhs);

    if (lhsId == rhsId) {
      return 0;
    }

    final int lhsRanking = getRanking(lhs);
    final int rhsRanking = getRanking(rhs);

    if (lhsRanking == rhsRanking) {
      // favour lower service id
      return lhsId < rhsId ? -1 : 1;
    }

    // but higher service ranking beats all
    return lhsRanking < rhsRanking ? 1 : -1;
  }

  private static long getId(final ServiceReference ref) {
    return (Long) ref.getProperty(SERVICE_ID);
  }

  private static int getRanking(final ServiceReference ref) {
    final Object rank = ref.getProperty(SERVICE_RANKING);
    return rank instanceof Integer ? (Integer) rank : 0;
  }
}
