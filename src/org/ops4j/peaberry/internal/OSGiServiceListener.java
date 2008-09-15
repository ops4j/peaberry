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

import static java.util.Collections.addAll;
import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;
import static org.osgi.framework.Constants.OBJECTCLASS;
import static org.osgi.framework.ServiceEvent.MODIFIED;
import static org.osgi.framework.ServiceEvent.REGISTERED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

/**
 * Keep track of OSGi services that provide a specific interface.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceListener
    implements ServiceListener {

  private static final Comparator<ServiceReference> BEST_SERVICE_COMPARATOR =
      new BestServiceComparator();

  final List<ServiceReference> services;
  final BundleContext bundleContext;

  public OSGiServiceListener(final BundleContext bundleContext, final String clazzName) {
    services = new ArrayList<ServiceReference>();
    this.bundleContext = bundleContext;

    try {
      synchronized (services) {
        bundleContext.addServiceListener(this, "(" + OBJECTCLASS + '=' + clazzName + ')');
        final ServiceReference[] initialRefs = bundleContext.getServiceReferences(clazzName, null);
        if (null != initialRefs) {
          addAll(services, initialRefs);
          sort(services, BEST_SERVICE_COMPARATOR);
        }
      }
    } catch (final InvalidSyntaxException e) {
      throw new RuntimeException(e);
    }
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

  public <T> Iterator<Import<T>> iterator(final Class<? extends T> type,
      final AttributeFilter filter) {
    return new Iterator<Import<T>>() {
      private Import<T> nextService;
      private int index;

      public boolean hasNext() {
        findNextService();
        return nextService != null;
      }

      public Import<T> next() {
        findNextService();
        if (null == nextService) {
          throw new ServiceUnavailableException();
        }
        try {
          return nextService;
        } finally {
          nextService = null;
        }
      }

      private void findNextService() {
        if (null == nextService) {
          try {
            while (true) {
              final ServiceReference ref;
              synchronized (services) {
                ref = services.get(index++);
              }
              if (null == filter || filter.matches(new ServiceAttributes(ref))) {
                nextService = getServiceImport(ref);
                break;
              }
            }
          } catch (final IndexOutOfBoundsException e) {}
        }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }

      private Import<T> getServiceImport(final ServiceReference ref) {
        return new Import<T>() {
          public T get() {
            try {
              final T obj = type.cast(bundleContext.getService(ref));
              if (null == obj) {
                throw new ServiceUnavailableException();
              }
              return obj;
            } catch (final Exception e) {
              throw new ServiceUnavailableException(e);
            }
          }

          public void unget() {
            try {
              bundleContext.ungetService(ref);
            } catch (final Exception e) {}
          }
        };
      }
    };
  }
}
