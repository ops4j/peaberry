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

import static org.osgi.framework.Constants.OBJECTCLASS;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
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

  final Map<String, OSGiServiceListener> listenerMap;
  final BundleContext bundleContext;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    listenerMap = new HashMap<String, OSGiServiceListener>();
    this.bundleContext = bundleContext;
  }

  public <T> Iterable<Import<T>> lookup(final Class<? extends T> type, final AttributeFilter filter) {
    final OSGiServiceListener listener = getServiceListener(type.getName());
    return new Iterable<Import<T>>() {
      public Iterator<Import<T>> iterator() {
        return listener.iterator(type, filter);
      }
    };
  }

  OSGiServiceListener getServiceListener(final String clazzName) {
    synchronized (listenerMap) {
      OSGiServiceListener listener = listenerMap.get(clazzName);
      if (null == listener) {
        listener = new OSGiServiceListener(bundleContext, clazzName);
        listenerMap.put(clazzName, listener);
      }
      return listener;
    }
  }

  public <T, S extends T> Export<T> export(final S service, final Map<String, ?> attributes) {

    // unfortunately, the OSGi API expects a Dictionary rather than a Map :(
    final Hashtable<String, Object> dictionary = new Hashtable<String, Object>();
    if (null != attributes) {
      dictionary.putAll(attributes);
    }

    final String[] interfaces;

    final Object objectClass = dictionary.get(OBJECTCLASS);
    if (objectClass instanceof String[]) {
      // use service attributes setting
      interfaces = (String[]) objectClass;
    } else {
      // use simple algorithm - don't search the hierarchy
      final Collection<String> api = new HashSet<String>();
      final Class<?> clazz = service.getClass();

      if (clazz.isInterface()) {
        api.add(clazz.getName());
      } else {
        // just use the interfaces directly declared for this class
        for (final Class<?> i : service.getClass().getInterfaces()) {
          api.add(i.getName());
        }
      }

      interfaces = api.toArray(new String[api.size()]);
    }

    final ServiceRegistration registration;

    try {
      registration = bundleContext.registerService(interfaces, service, dictionary);
    } catch (final Exception e) {
      throw new ServiceException(e);
    }

    // wrap registration as export
    return new Export<T>() {

      public T get() {
        return service;
      }

      public void unget() {}

      public void modify(final Map<String, ?> map) {

        // unfortunately, the OSGi API expects a Dictionary rather than a Map :(
        final Hashtable<String, Object> dict = new Hashtable<String, Object>();
        if (null != map) {
          dict.putAll(map);
        }

        try {
          registration.setProperties(dict);
        } catch (final Exception e) {
          throw new ServiceException(e);
        }
      }

      public void remove() {
        try {
          registration.unregister();
        } catch (final Exception e) {}
      }
    };
  }

  @Override
  public String toString() {
    return String.format("OSGiServiceRegistry(%s)", bundleContext.getBundle());
  }
}
