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
import java.util.NoSuchElementException;

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
        } catch (final Exception e) {
          throw new ServiceException(e);
        }

        if (services != null) {
          Arrays.sort(services, BEST_SERVICE_COMPARATOR);
        }

        return new Iterator<Import<T>>() {
          int i = 0;

          public boolean hasNext() {
            return services != null && i < services.length;
          }

          @SuppressWarnings("null")
          public Import<T> next() {
            final ServiceReference ref;
            try {
              ref = services[i++];
            } catch (final Exception e) {
              throw new NoSuchElementException();
            }

            return new Import<T>() {
              public T get() {
                try {
                  final T obj = type.cast(bundleContext.getService(ref));
                  obj.getClass(); // force NPE if null
                  return obj;
                } catch (final Exception e) {
                  throw new ServiceUnavailableException(e);
                }
              }

              public void unget() {
                try {
                  bundleContext.ungetService(ref);
                } catch (final Exception e) {}
              }
            };
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

    final ServiceRegistration registration;

    try {
      registration = bundleContext.registerService(interfaces, service, dictionary);
    } catch (final Exception e) {
      throw new ServiceException(e);
    }

    return new Export<T>() {

      @SuppressWarnings("unchecked")
      public T get() {
        return service;
      }

      public void unget() {}

      public void modify(final Map<String, ?> map) {
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
