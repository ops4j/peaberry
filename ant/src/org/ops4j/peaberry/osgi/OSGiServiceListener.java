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

import static org.osgi.framework.ServiceEvent.MODIFIED;
import static org.osgi.framework.ServiceEvent.REGISTERED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.cache.AbstractServiceListener;
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
final class OSGiServiceListener<T>
    extends AbstractServiceListener<T>
    implements ServiceListener {

  private final BundleContext bundleContext;
  private final String ldapFilter;

  OSGiServiceListener(final BundleContext bundleContext, final String ldapFilter) {
    this.bundleContext = bundleContext;
    this.ldapFilter = ldapFilter;
  }

  @Override
  protected synchronized void start() {
    try {

      // register listener first to avoid race condition
      bundleContext.addServiceListener(this, ldapFilter);

      // retrieve snapshot of any matching services that are already registered
      final ServiceReference[] refs = bundleContext.getServiceReferences(null, ldapFilter);
      if (null != refs) {

        // wrap service references to optimize sorting
        for (final ServiceReference r : refs) {
          insertService(new OSGiServiceImport<T>(bundleContext, r)); // NOPMD
        }
      }

    } catch (final InvalidSyntaxException e) {
      throw new ServiceException(e); // this should never happen!
    }
  }

  public synchronized void serviceChanged(final ServiceEvent event) {

    final OSGiServiceImport<T> service =
        new OSGiServiceImport<T>(bundleContext, event.getServiceReference());

    switch (event.getType()) {
    case REGISTERED:
      insertService(service);
      break;
    case MODIFIED:
      updateService(service);
      break;
    case UNREGISTERING:
      removeService(service);
      break;
    default:
      break;
    }
  }
}
