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

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * OSGi {@link ServiceRegistry} adaptor (proof-of-concept, not optimised)
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Singleton
public final class OSGiServiceRegistry
    implements ServiceRegistry {

  static final Comparator<ServiceReference> BEST_SERVICE_COMPARATOR = new BestServiceComparator();

  /**
   * Current bundle context, used to interrogate the registry.
   */
  final BundleContext bundleContext;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  public <T> Iterable<Import<T>> lookup(final Class<? extends T> type, final String filter) {

    /*
     * This is just a quick proof-of-concept implementation, it doesn't track
     * services and suffers from potential race conditions. A production ready
     * implementation will be available soon.
     */

    return new Iterable<Import<T>>() {
      public Iterator<Import<T>> iterator() {

        final ServiceReference[] services;

        try {
          services = bundleContext.getServiceReferences(type.getName(), filter);
          if (services != null) {
            Arrays.sort(services, BEST_SERVICE_COMPARATOR);
          }
        } catch (final Exception e) {
          throw new ServiceException(e);
        }

        return new Iterator<Import<T>>() {
          int i = 0;

          public boolean hasNext() {
            return services != null && i < services.length;
          }

          @SuppressWarnings("null")
          public Import<T> next() {
            try {
              final ServiceReference ref = services[i++];
              return new Import<T>() {
                public T get() {
                  return type.cast(bundleContext.getService(ref));
                }

                public void unget() {
                  bundleContext.ungetService(ref);
                }
              };
            } catch (final Exception e) {
              throw new ServiceUnavailableException(e);
            }
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  public <T, S extends T> Export<T> export(final S service, final Map<String, ?> attributes) {

    final Hashtable<String, Object> dictionary = new Hashtable<String, Object>();
    if (null != attributes) {
      dictionary.putAll(attributes);
    }

    /*
     * investigate various ways to determine service API...
     */
    String[] interfaces;
    final Object objectclass = dictionary.get(OBJECTCLASS);
    if (objectclass instanceof String[]) {
      interfaces = (String[]) objectclass;
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
        bundleContext.registerService(interfaces, service, dictionary);

    return new Export<T>() {

      @SuppressWarnings("unchecked")
      public T get() {
        try {
          return (T) bundleContext.getService(registration.getReference());
        } catch (final IllegalStateException e) {
          return null;
        }
      }

      public void unget() {
        try {
          bundleContext.ungetService(registration.getReference());
        } catch (final IllegalStateException e) {}
      }

      public void modify(final Map<String, ?> map) {
        final Hashtable<String, Object> dict = new Hashtable<String, Object>();
        if (null != map) {
          dict.putAll(map);
        }
        registration.setProperties(dict);
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
