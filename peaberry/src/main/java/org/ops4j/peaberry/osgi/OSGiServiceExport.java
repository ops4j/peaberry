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
import org.ops4j.peaberry.util.SimpleExport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * {@link Export} implementation backed by an OSGi {@link ServiceRegistration}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceExport<T>
    extends SimpleExport<T> {

  private final BundleContext bundleContext;
  private ServiceRegistration reg;

  OSGiServiceExport(final BundleContext bundleContext, final Import<T> service) {
    super(service);
    this.bundleContext = bundleContext;
    exportOSGiService();
  }

  @Override
  public synchronized void put(final T newInstance) {
    removeOSGiService();
    super.put(newInstance);
    exportOSGiService();
  }

  @Override
  public synchronized void attributes(final Map<String, ?> newAttributes) {
    super.attributes(newAttributes);
    if (null != reg) {
      reg.setProperties(getProperties(newAttributes));
    }
  }

  private void exportOSGiService() {
    final T instance = get();
    if (null != instance) {
      final Dictionary<String, ?> properties = getProperties(attributes());
      final String[] interfaceNames = getInterfaceNames(instance, properties);
      reg = bundleContext.registerService(interfaceNames, instance, properties);
    }
  }

  private void removeOSGiService() {
    if (null != reg) {
      try {
        reg.unregister();
      } catch (final RuntimeException re) {/* already gone */} // NOPMD
      reg = null;
    }
  }

  private static Dictionary<String, ?> getProperties(final Map<String, ?> attributes) {
    return null == attributes || attributes.isEmpty() ? null : new AttributeDictionary(attributes);
  }

  private static String[] getInterfaceNames(final Object instance, final Dictionary<?, ?> properties) {
    final Object objectClass = null == properties ? null : properties.get(OBJECTCLASS);

    // check service attributes setting
    if (objectClass instanceof String[]) {
      return (String[]) objectClass;
    }

    final Set<String> names = new HashSet<String>();

    for (Class<?> clazz = instance.getClass(); null != clazz; clazz = clazz.getSuperclass()) {
      for (final Class<?> i : clazz.getInterfaces()) {
        names.add(i.getName());
      }
    }

    return names.toArray(new String[names.size()]);
  }
}
