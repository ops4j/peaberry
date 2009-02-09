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
import static org.osgi.framework.Constants.OBJECTCLASS;
import static org.osgi.framework.ServiceEvent.MODIFIED;
import static org.osgi.framework.ServiceEvent.REGISTERED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.ArrayList;
import java.util.List;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceScope;
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

  private final BundleContext bundleContext;
  private final String clazzFilter;

  private final List<OSGiServiceImport> imports;
  private final List<ServiceScope<Object>> watchers;

  OSGiServiceListener(final BundleContext bundleContext, final String clazzName) {
    this.bundleContext = bundleContext;

    if (OBJECT_CLAZZ_NAME.equals(clazzName)) {
      clazzFilter = null; // match all registered services
    } else {
      clazzFilter = '(' + OBJECTCLASS + '=' + clazzName + ')';
    }

    imports = new ArrayList<OSGiServiceImport>(4);
    watchers = new ArrayList<ServiceScope<Object>>(2);
  }

  synchronized void start() {
    try {

      // register listener first to avoid race condition
      bundleContext.addServiceListener(this, clazzFilter);

      // retrieve snapshot of any matching services that are already registered
      final ServiceReference[] refs = bundleContext.getServiceReferences(null, clazzFilter);
      if (null != refs) {

        // wrap service references to optimize sorting
        for (final ServiceReference r : refs) {
          insertService(new OSGiServiceImport(bundleContext, r)); // NOPMD
        }
      }

    } catch (final InvalidSyntaxException e) {
      throw new ServiceException(e); // this should never happen!
    }
  }

  public synchronized void serviceChanged(final ServiceEvent event) {
    final OSGiServiceImport i = new OSGiServiceImport(bundleContext, event.getServiceReference());

    switch (event.getType()) {
    case REGISTERED:
      insertService(i);
      break;
    case MODIFIED:
      updateService(i);
      break;
    case UNREGISTERING:
      removeService(i);
      break;
    default:
      break;
    }
  }

  @SuppressWarnings("unchecked")
  synchronized void addWatcher(final ServiceScope scope) {
    if (!watchers.contains(scope) && watchers.add(scope)) {

      // report existing imports to the new scope
      for (final OSGiServiceImport i : imports) {
        final Export export = scope.add(i);
        if (null != export) {
          i.addWatcher(export);
        }
      }
    }
  }

  synchronized void flush(final int targetGeneration) {
    // flush any unused cached service instances
    for (final OSGiServiceImport i : imports) {
      i.flush(targetGeneration);
    }
  }

  private void insertService(final OSGiServiceImport i) {
    // find insertion point that maintains ordering
    final int insertIndex = binarySearch(imports, i);

    if (insertIndex < 0) {
      // new object, must flip index
      imports.add(~insertIndex, i);

      // report new import to any watching scopes
      for (final ServiceScope<Object> scope : watchers) {
        final Export<Object> export = scope.add(i);
        if (null != export) {
          i.addWatcher(export);
        }
      }
    }
  }

  private void updateService(final OSGiServiceImport i) {
    // use linear search in case ranking has changed
    final int index = imports.indexOf(i);

    if (index >= 0) {
      // keep existing instance as it might be in use
      final OSGiServiceImport orig = imports.get(index);

      // need to re-order list?
      if (orig.updateRanking()) {
        imports.remove(index);
        imports.add(~binarySearch(imports, orig), orig);
      }
    } else {
      // not seen before
      insertService(i);
    }
  }

  private void removeService(final OSGiServiceImport i) {
    // use linear search in case ranking has changed
    final int index = imports.indexOf(i);
    if (index >= 0) {
      // flush cache even if being used
      imports.remove(index).invalidate();
    }
  }

  synchronized OSGiServiceImport findNextImport(final OSGiServiceImport prevImport,
      final AttributeFilter filter) {

    if (imports.isEmpty()) {
      return null;
    }

    if (prevImport == null && filter == null) {
      return imports.get(0);
    }

    // estimate last position based on previous value and current list
    return findNextImport(filter, null == prevImport ? ~0 : binarySearch(imports, prevImport));
  }

  private OSGiServiceImport findNextImport(final AttributeFilter filter, final int prevIndex) {

    // may need to flip position if previous import is no longer in the list
    for (int i = prevIndex < 0 ? ~prevIndex : prevIndex + 1; i < imports.size(); i++) {

      // now do a linear search applying the given filter
      final OSGiServiceImport nextImport = imports.get(i);
      if (null == filter || nextImport.matches(filter)) {
        return nextImport;
      }
    }

    return null; // no matching service
  }
}
