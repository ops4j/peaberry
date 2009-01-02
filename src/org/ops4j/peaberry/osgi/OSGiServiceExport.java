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

import static org.osgi.framework.Constants.OBJECTCLASS;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * {@link Export} implementation backed by an OSGi {@link ServiceRegistration}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceExport<T>
    implements Export<T> {

  private final BundleContext bundleContext;
  private Import<T> originalService;

  private T instance;
  private Map<String, ?> attributes;
  private ServiceRegistration reg;

  public OSGiServiceExport(final BundleContext bundleContext, final Import<T> service) {
    this.bundleContext = bundleContext;
    this.originalService = service;

    // grab service so it can be exported
    instance = service.get();
    attributes = service.attributes();

    exportOSGiService();
  }

  // Import aspect...

  public synchronized T get() {
    return instance;
  }

  public synchronized Map<String, ?> attributes() {
    return attributes;
  }

  public void unget() {/* nothing to do */}

  // Export aspect...

  public synchronized void put(final T newInstance) {
    if (newInstance != instance) { // NOPMD
      removeOSGiService();
      instance = newInstance;
      exportOSGiService();
    }
  }

  public synchronized void attributes(final Map<String, ?> newAttributes) {
    attributes = newAttributes;
    if (null != reg) {
      reg.setProperties(getProperties());
    }
  }

  public synchronized void unput() {
    removeOSGiService();
  }

  private void exportOSGiService() {
    if (null != instance) {
      reg = bundleContext.registerService(getInterfaceNames(), instance, getProperties());
    }
  }

  private void removeOSGiService() {

    // first remove the registered service
    if (null != reg) {
      try {
        reg.unregister();
      } catch (final RuntimeException re) {/* ignore */} // NOPMD
      reg = null;
    }

    // can now release the original service
    if (null != originalService) {
      try {
        originalService.unget();
      } catch (final RuntimeException re) {/* ignore */} // NOPMD
      originalService = null;
    }
  }

  private String[] getInterfaceNames() {

    final Object objectClass = null == attributes ? null : attributes.get(OBJECTCLASS);

    // check service attributes setting
    if (objectClass instanceof String[]) {
      return (String[]) objectClass;
    }

    final Set<String> names = new HashSet<String>();

    Class<?> clazz = instance.getClass();
    while (clazz != null) {
      if (clazz.isInterface()) {
        names.add(clazz.getName());
      } else {
        // use direct interfaces declared by this class
        for (final Class<?> i : clazz.getInterfaces()) {
          names.add(i.getName());
        }
      }
      clazz = clazz.getSuperclass();
    }

    return names.toArray(new String[names.size()]);
  }

  private Dictionary<String, ?> getProperties() {
    return null == attributes ? null : new AttributeDictionary(attributes);
  }
}
