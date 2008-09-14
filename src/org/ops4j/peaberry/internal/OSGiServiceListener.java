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

import static java.util.Collections.binarySearch;
import static org.osgi.framework.ServiceEvent.MODIFIED;
import static org.osgi.framework.ServiceEvent.REGISTERED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceListener
    implements ServiceListener {

  private static final Comparator<ServiceReference> BEST_SERVICE_COMPARATOR =
      new BestServiceComparator();

  final List<ServiceReference> services;

  public OSGiServiceListener() {
    services = new ArrayList<ServiceReference>();
  }

  public void serviceChanged(final ServiceEvent event) {
    final ServiceReference ref = event.getServiceReference();

    synchronized (services) {
      switch (event.getType()) {
      case REGISTERED:
        insertService(ref);
        break;
      case MODIFIED:
        services.remove(ref);
        insertService(ref);
        break;
      case UNREGISTERING:
        services.remove(ref);
        break;
      default:
        break;
      }
    }
  }

  private void insertService(final ServiceReference ref) {
    final int insertIndex = binarySearch(services, ref, BEST_SERVICE_COMPARATOR);
    if (insertIndex < 0) {
      services.add(~insertIndex, ref);
    }
  }

  public Iterator<ServiceReference> iterator(final AttributeFilter filter) {
    return new Iterator<ServiceReference>() {
      private ServiceReference nextRef;
      private int index;

      public boolean hasNext() {
        if (null == nextRef) {
          nextRef = findNextService();
        }
        return nextRef != null;
      }

      public ServiceReference next() {
        if (null == nextRef) {
          nextRef = findNextService();
        }
        try {
          if (null == nextRef) {
            throw new ServiceUnavailableException();
          }
          return nextRef;
        } finally {
          nextRef = null;
        }
      }

      private ServiceReference findNextService() {
        synchronized (services) {
          while (index < services.size()) {
            final ServiceReference ref = services.get(index++);
            if (filter.matches(new ServiceAttributes(ref))) {
              return ref;
            }
          }
          return null;
        }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
