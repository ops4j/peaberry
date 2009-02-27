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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

/**
 * OSGi {@link ServiceRegistry} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class OSGiServiceRegistry
    implements CachingServiceRegistry {

  private final BundleContext bundleContext;

  // per-class map of service listeners (much faster than polling)
  private final ConcurrentMap<String, OSGiServiceListener> listenerMap =
      new ConcurrentHashMap<String, OSGiServiceListener>();

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {
    return new IterableOSGiService<T>(registerListener(clazz), filter);
  }

  public <T> Export<T> add(final Import<T> service) {
    return new OSGiServiceExport<T>(bundleContext, service);
  }

  @SuppressWarnings("unchecked")
  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceScope<? super T> scope) {

    registerListener(clazz).addWatcher(
        null == filter ? scope : new FilteredServiceScope(filter, scope));
  }

  public void flush(final int targetGeneration) {
    // look for unused cached service instances to flush...
    for (final OSGiServiceListener i : listenerMap.values()) {
      i.flush(targetGeneration);
    }
  }

  @Override
  public String toString() {
    return String.format("OSGiServiceRegistry[%s]", bundleContext.getBundle());
  }

  @Override
  public int hashCode() {
    return bundleContext.hashCode();
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof OSGiServiceRegistry) {
      return bundleContext.equals(((OSGiServiceRegistry) rhs).bundleContext);
    }
    return false;
  }

  private <T> OSGiServiceListener registerListener(final Class<T> clazz) {
    final String clazzName = (null == clazz ? Object.class : clazz).getName();
    OSGiServiceListener listener;

    listener = listenerMap.get(clazzName);
    if (null == listener) {
      final OSGiServiceListener newListener = new OSGiServiceListener(bundleContext, clazzName);
      listener = listenerMap.putIfAbsent(clazzName, newListener);
      // /CLOVER:OFF - rare event
      if (null == listener) {
        // /CLOVER:ON
        newListener.start();
        return newListener;
      }
    }

    return listener;
  }
}
