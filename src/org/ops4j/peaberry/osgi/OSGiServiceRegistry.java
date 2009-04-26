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

import static org.ops4j.peaberry.Peaberry.NATIVE_FILTER_HINT;
import static org.ops4j.peaberry.util.Filters.ldap;
import static org.osgi.framework.Constants.OBJECTCLASS;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;
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
  private final boolean useNativeFilter;

  // per-class/filter map of service listeners (much faster than polling)
  private final ConcurrentMap<String, OSGiServiceListener> listenerMap =
      new ConcurrentHashMap<String, OSGiServiceListener>(16, 0.75f, 2);

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    this.bundleContext = bundleContext;

    useNativeFilter = Boolean.valueOf(bundleContext.getProperty(NATIVE_FILTER_HINT));
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {

    // might combine class filter and user filter as one LDAP string
    final AttributeFilter[] filterRef = new AttributeFilter[]{filter};
    final String ldapFilter = getLdapFilter(clazz, filterRef);

    return new IterableOSGiService<T>(registerListener(ldapFilter), filterRef[0]);
  }

  public <T> Export<T> add(final Import<T> service) {
    // avoid cycles by ignoring our own services
    if (service instanceof OSGiServiceImport) {
      return null;
    }
    return new OSGiServiceExport<T>(bundleContext, service);
  }

  @SuppressWarnings("unchecked")
  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceWatcher<? super T> watcher) {

    // might combine class filter and user filter as one LDAP string
    final AttributeFilter[] filterRef = new AttributeFilter[]{filter};
    final String ldapFilter = getLdapFilter(clazz, filterRef);

    if (null == filterRef[0]) {
      registerListener(ldapFilter).addWatcher(watcher);
    } else {
      registerListener(ldapFilter).addWatcher(new FilteredServiceWatcher(filter, watcher));
    }
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

  private OSGiServiceListener registerListener(final String filter) {
    final String key = null == filter ? "" : filter;
    OSGiServiceListener listener;

    listener = listenerMap.get(key);
    if (null == listener) {
      final OSGiServiceListener newListener = new OSGiServiceListener(bundleContext, filter);
      listener = listenerMap.putIfAbsent(key, newListener);
      if (null == listener) {
        newListener.start();
        return newListener;
      }
    }

    return listener;
  }

  private String getLdapFilter(final Class<?> clazz, final AttributeFilter[] filterRef) {
    final String clazzFilter;

    if (null != clazz && Object.class != clazz) { // NOPMD
      clazzFilter = '(' + OBJECTCLASS + '=' + clazz.getName() + ')';
    } else {
      clazzFilter = null;
    }

    if (useNativeFilter && null != filterRef[0]) {
      try {
        // can the user filter object be normalized to an LDAP string?
        final String filter = ldap(filterRef[0].toString()).toString();
        filterRef[0] = null; // yes, so we don't need object anymore

        return null == clazzFilter ? filter : "(&" + clazzFilter + filter + ')';
      } catch (final IllegalArgumentException e) {/* not native LDAP */} // NOPMD
    }

    return clazzFilter;
  }
}
