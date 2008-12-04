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
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceScope;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * OSGi specific {@code ServiceRegistry} adapter.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Singleton
public final class OSGiServiceRegistry
    implements CachingServiceRegistry {

  // per-class map of service listeners (much faster than polling)
  private final ConcurrentMap<String, OSGiServiceListener> listenerMap;
  private final BundleContext bundleContext;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {

    listenerMap = new ConcurrentHashMap<String, OSGiServiceListener>();
    this.bundleContext = bundleContext;

    // register this as a caching registry, so it can be regularly flushed...
    bundleContext.registerService(CachingServiceRegistry.class.getName(), this, null);
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> type, final AttributeFilter filter) {
    final String clazzName = type.getName();
    OSGiServiceListener listener;

    /*** !!! DEFER UNTIL ACTUALLY NEEDED ??? ***/

    listener = listenerMap.get(clazzName);
    if (null == listener) {
      final OSGiServiceListener newListener = new OSGiServiceListener(bundleContext, clazzName);
      listener = listenerMap.putIfAbsent(clazzName, newListener);
      if (null == listener) {
        listener = newListener;
        newListener.start();
      }
    }

    // wrap filtered iterable around unfiltered list
    return new IterableOSGiService<T>(listener, filter);
  }

  public <T> void watch(Class<T> clazz, AttributeFilter filter, ServiceScope<T> scope) {
  // TODO Auto-generated method stub
  }

  public <T> Export<T> add(final Import<T> service) {

    // map generic service attributes to OSGi expected types
    final Dictionary<String, ?> props = dictionary(service.attributes());
    final String[] interfaces = getInterfaceNames(service.get(), props);

    final ServiceRegistration registration =
        bundleContext.registerService(interfaces, service.get(), props);

    // wrap registration as export
    return new Export<T>() {

      public T get() {
        return service.get();
      }

      public Map<String, ?> attributes() {
        return service.attributes();
      }

      public void unget() {
        service.unget();
      }

      public void put(final T newService) {
        throw new UnsupportedOperationException();
      }

      public void attributes(final Map<String, ?> newAttributes) {
        registration.setProperties(dictionary(newAttributes));
      }

      public void unput() {
        registration.unregister();
      }
    };
  }

  public void flush() {
    // look for unused cached service instances to flush...
    for (final OSGiServiceListener i : listenerMap.values()) {
      i.flush();
    }
  }

  private String[] getInterfaceNames(final Object service, final Dictionary<String, ?> props) {
    final Object objectClass = null == props ? null : props.get(OBJECTCLASS);
    final String[] interfaces;

    // check service attributes setting
    if (objectClass instanceof String[]) {
      interfaces = (String[]) objectClass;
    } else {
      final Collection<String> api = new HashSet<String>();

      Class<?> clazz = service.getClass();
      while (clazz != null) {
        if (clazz.isInterface()) {
          api.add(clazz.getName());
        } else {
          // use direct interfaces declared by this class
          for (final Class<?> i : clazz.getInterfaces()) {
            api.add(i.getName());
          }
        }
        clazz = clazz.getSuperclass();
      }

      interfaces = api.toArray(new String[api.size()]);
    }

    if (interfaces.length == 0) {
      return new String[] {Object.class.getName()};
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
