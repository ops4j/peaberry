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

import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;
import static org.osgi.framework.Constants.OBJECTCLASS;
import static org.osgi.framework.ServiceEvent.REGISTERED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.ArrayList;
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
 * Keep track of imported OSGi services that provide a specific interface.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceListener
    implements ServiceListener {

  private static final String OBJECT_CLAZZ_NAME = Object.class.getName();

  private final List<OSGiServiceImport> imports;
  private final BundleContext bundleContext;

  private final Lock writeLock;
  private final Lock readLock;

  public OSGiServiceListener(final BundleContext bundleContext, final String clazzName) {
    imports = new ArrayList<OSGiServiceImport>();
    this.bundleContext = bundleContext;

    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

    writeLock = rwl.writeLock();
    readLock = rwl.readLock();

    final String filter = getClazzFilter(clazzName);

    writeLock.lock();
    try {

      bundleContext.addServiceListener(this, filter);
      final ServiceReference[] initialRefs = bundleContext.getServiceReferences(null, filter);
      if (null != initialRefs) {
        for (final ServiceReference ref : initialRefs) {
          imports.add(new OSGiServiceImport(bundleContext, ref));
        }
        if (imports.size() > 1) {
          sort(imports);
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
    final OSGiServiceImport i = new OSGiServiceImport(bundleContext, event.getServiceReference());
    writeLock.lock();
    try {

      switch (event.getType()) {
      case REGISTERED:
        insertService(i);
        break;
      case UNREGISTERING:
        imports.remove(i);
        break;
      default:
        imports.remove(i);
        insertService(i);
        break;
      }

    } finally {
      writeLock.unlock();
    }
  }

  private void insertService(final OSGiServiceImport i) {
    final int insertIndex = binarySearch(imports, i);
    if (insertIndex < 0) {
      imports.add(~insertIndex, i);
    }
  }

  public OSGiServiceImport findNextImport(final OSGiServiceImport prevImport,
      final AttributeFilter filter) {

    readLock.lock();
    try {

      // tail optimization
      if (imports.isEmpty() || imports.get(imports.size() - 1).equals(prevImport)) {
        return null;
      }
      // head optimization
      if (prevImport == null && filter == null) {
        return imports.get(0);
      }

      return findNextImport(filter, null == prevImport ? ~0 : binarySearch(imports, prevImport));

    } finally {
      readLock.unlock();
    }
  }

  private OSGiServiceImport findNextImport(final AttributeFilter filter, final int prevIndex) {

    for (int i = prevIndex < 0 ? ~prevIndex : prevIndex + 1; i < imports.size(); i++) {
      final OSGiServiceImport nextImport = imports.get(i);
      if (null == filter || filter.matches(nextImport.getAttributes())) {
        return nextImport;
      }
    }

    return null;
  }
}
