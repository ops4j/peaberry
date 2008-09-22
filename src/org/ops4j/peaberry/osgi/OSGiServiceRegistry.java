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

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * OSGi specific {@code ServiceRegistry} adaptor.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Singleton
public final class OSGiServiceRegistry
    implements ServiceRegistry {

  private final Map<String, OSGiServiceListener> listenerMap;
  private final BundleContext bundleContext;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    listenerMap = new HashMap<String, OSGiServiceListener>();
    this.bundleContext = bundleContext;
  }

  public <T> Iterable<Import<T>> lookup(final Class<? extends T> type, final AttributeFilter filter) {
    final String clazzName = type.getName();
    OSGiServiceListener listener;

    synchronized (listenerMap) {
      listener = listenerMap.get(clazzName);
      if (null == listener) {
        listener = new OSGiServiceListener(bundleContext, clazzName);
        listenerMap.put(clazzName, listener);
      }
    }

    return new IterableOSGiService<T>(listener, filter);
  }

  public <T, S extends T> Export<T> export(final S service, final Map<String, ?> attributes) {
    final Dictionary<String, ?> props = dictionary(attributes);
    final String[] interfaces = getInterfaceNames(service, props);

    final ServiceRegistration registration =
        bundleContext.registerService(interfaces, service, props);

    // wrap registration as export
    return new Export<T>() {

      public T get() {
        return service;
      }

      public void unget() {/* nothing to do */}

      public void modify(final Map<String, ?> newAttributes) {
        registration.setProperties(dictionary(newAttributes));
      }

      public void remove() {
        registration.unregister();
      }
    };
  }

  private <S> String[] getInterfaceNames(final S service, final Dictionary<String, ?> props) {
    final Object objectClass = null == props ? null : props.get(OBJECTCLASS);
    final String[] interfaces;

    // check service attributes setting
    if (objectClass instanceof String[]) {
      interfaces = (String[]) objectClass;
    } else {
      // use simple algorithm - don't search the hierarchy
      final Collection<String> api = new HashSet<String>();
      final Class<?> clazz = service.getClass();

      if (clazz.isInterface()) {
        api.add(clazz.getName());
      } else {
        // just use the interfaces declared for this class
        for (final Class<?> i : clazz.getInterfaces()) {
          api.add(i.getName());
        }
      }

      interfaces = api.toArray(new String[api.size()]);
    }
    return interfaces;
  }

  // unfortunately the OSGi API expects Dictionary rather than Map :(
  static Dictionary<String, ?> dictionary(final Map<String, ?> attributes) {
    return null == attributes ? null : new AttributeDictionary(attributes);
  }

  @Override
  public String toString() {
    return String.format("OSGiServiceRegistry(%s)", bundleContext.getBundle());
  }
}
