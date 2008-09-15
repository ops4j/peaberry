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
import static org.osgi.framework.ServiceEvent.REGISTERED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceException;
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

  private static final Comparator<ServiceReference> BEST_SERVICE = new BestServiceComparator();
  private static final String OBJECT_CLAZZ_NAME = Object.class.getName();

  private final List<ServiceReference> services;

  private final Lock writeLock;
  private final Lock readLock;

  public OSGiServiceListener(final BundleContext bundleContext, final String clazzName) {
    services = new ArrayList<ServiceReference>();

    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
    writeLock = rwl.writeLock();
    readLock = rwl.readLock();

    final String filter = getClazzFilter(clazzName);

    writeLock.lock();
    try {

      bundleContext.addServiceListener(this, filter);
      final ServiceReference[] initialRefs = bundleContext.getServiceReferences(null, filter);
      if (null != initialRefs) {
        addAll(services, initialRefs);
        if (services.size() > 1) {
          sort(services, BEST_SERVICE);
        }
      }

    } catch (final InvalidSyntaxException e) {
      throw new ServiceException(e);
    } finally {
      writeLock.unlock();
    }
  }

  private String getClazzFilter(final String clazzName) {
    return OBJECT_CLAZZ_NAME.equals(clazzName) ? null : '(' + OBJECTCLASS + '=' + clazzName + ')';
  }

  public void serviceChanged(final ServiceEvent event) {
    final ServiceReference ref = event.getServiceReference();
    writeLock.lock();
    try {

      switch (event.getType()) {
      case REGISTERED:
        insertService(ref);
        break;
      case UNREGISTERING:
        services.remove(ref);
        break;
      default:
        services.remove(ref);
        insertService(ref);
        break;
      }

    } finally {
      writeLock.unlock();
    }
  }

  private void insertService(final ServiceReference ref) {
    final int insertIndex = binarySearch(services, ref, BEST_SERVICE);
    if (insertIndex < 0) {
      services.add(~insertIndex, ref);
    }
  }

  public ServiceReference findNextService(final ServiceReference prev, final AttributeFilter filter) {
    readLock.lock();
    try {

      // tail optimization
      if (services.isEmpty() || services.get(services.size() - 1) == prev) {
        return null;
      }
      // head optimization
      if (prev == null && filter == null) {
        return services.get(0);
      }

      return findNextService(filter, null == prev ? ~0 : binarySearch(services, prev, BEST_SERVICE));

    } finally {
      readLock.unlock();
    }
  }

  private ServiceReference findNextService(final AttributeFilter filter, final int prevIndex) {
    final OSGiServiceAttributes attributes = new OSGiServiceAttributes();

    for (int i = prevIndex < 0 ? ~prevIndex : prevIndex + 1; i < services.size(); i++) {
      final ServiceReference next = services.get(i);
      if (null == filter || filter.matches(attributes.reset(next))) {
        return next;
      }
    }

    return null;
  }
}
