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
import com.google.inject.Singleton;

/**
 * OSGi specific {@link ServiceRegistry} adapter.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Singleton
public final class OSGiServiceRegistry
    implements CachingServiceRegistry {

  private final BundleContext bundleContext;

  // per-class map of service listeners (much faster than polling)
  private final ConcurrentMap<String, OSGiServiceListener> listenerMap;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    this.bundleContext = bundleContext;

    listenerMap = new ConcurrentHashMap<String, OSGiServiceListener>();

    // register this as a caching registry, so it can be regularly flushed...
    bundleContext.registerService(CachingServiceRegistry.class.getName(), this, null);
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> type, final AttributeFilter filter) {
    final String clazzName = type.getName();
    OSGiServiceListener listener;

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

  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceScope<? super T> scope) {
  // TODO add support for watching services!
  }

  public <T> Export<T> add(final Import<T> service) {
    return new OSGiServiceExport<T>(bundleContext, service);
  }

  public void flush() {
    // look for unused cached service instances to flush...
    for (final OSGiServiceListener i : listenerMap.values()) {
      i.flush();
    }
  }

  @Override
  public String toString() {
    return String.format("OSGiServiceRegistry(%s)", bundleContext.getBundle());
  }
}
