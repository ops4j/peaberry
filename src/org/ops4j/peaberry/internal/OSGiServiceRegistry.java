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

import static com.google.inject.internal.base.Objects.nonNull;
import static java.util.Collections.reverseOrder;
import static org.osgi.framework.Constants.OBJECTCLASS;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Inject;

/**
 * OSGi {@link ServiceRegistry} adaptor (proof-of-concept, not optimised)
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiServiceRegistry
    implements ServiceRegistry {

  private static final Comparator<ServiceReference> SERVICE_COMPARATOR = new ServiceComparator();

  /**
   * Current bundle context, used to interrogate the registry.
   */
  final BundleContext bundleContext;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  /**
   * {@inheritDoc}
   */
  public <T> Iterator<T> lookup(final Class<? extends T> type, final String filter) {

    /*
     * This is just a quick proof-of-concept implementation, it doesn't track
     * services and suffers from potential race conditions. A production ready
     * implementation will be available soon.
     */

    final ServiceReference[] services;

    try {
      services = bundleContext.getServiceReferences(null, filter);
      if (services != null) {
        Arrays.sort(services, reverseOrder(SERVICE_COMPARATOR));
      }
    } catch (final Exception e) {
      throw new ServiceException(e);
    }

    return new Iterator<T>() {
      int i = 0;

      public boolean hasNext() {
        return services != null && i < services.length;
      }

      @SuppressWarnings("null")
      public T next() {
        try {
          return type.cast(bundleContext.getService(services[i++]));
        } catch (final Exception e) {
          throw new ServiceUnavailableException(e);
        }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  public <T, S extends T> Handle<T> add(final S service, final Map<String, ?> attributes) {

    nonNull(service, "service");

    String[] interfaces;

    /*
     * investigate various ways to determine service API...
     */
    final Object objectclass = attributes.get(OBJECTCLASS);
    if (objectclass instanceof String[]) {
      interfaces = (String[]) objectclass;
    } else if (objectclass instanceof String) {
      interfaces = ((String) objectclass).split(",");
      for (int i = 0; i < interfaces.length; i++) {
        interfaces[i] = interfaces[i].trim();
      }
    } else {
      final Collection<String> api = new HashSet<String>();
      final Class<?> clazz = service.getClass();
      if (clazz.isInterface()) {
        api.add(clazz.getName());
      } else {
        for (final Class<?> i : service.getClass().getInterfaces()) {
          api.add(i.getName());
        }
      }
      interfaces = api.toArray(new String[api.size()]);
    }

    final ServiceRegistration registration =
        bundleContext.registerService(interfaces, service, new Hashtable<String, Object>(attributes));

    return new Handle<T>() {

      @SuppressWarnings("unchecked")
      public T get() {
        try {
          return (T) bundleContext.getService(registration.getReference());
        } catch (final IllegalStateException e) {
          return null;
        }
      }

      public void modify(final Map<String, ?> attrs) {
        registration.setProperties(new Hashtable<String, Object>(attrs));
      }

      public void remove() {
        registration.unregister();
      }
    };
  }

  @Override
  public String toString() {
    return String.format("OSGiServiceRegistry(%s)", bundleContext.getBundle());
  }
}
