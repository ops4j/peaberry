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

import java.util.ArrayList;
import java.util.Iterator;

import org.ops4j.peaberry.ServiceRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiServiceRegistry
    implements ServiceRegistry {

  final BundleContext bundleContext;

  public OSGiServiceRegistry(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  /**
   * {@inheritDoc}
   */
  public <T> Iterator<T> lookup(final Class<? extends T> type, String filter) {

    final ServiceReference[] services;

    try {
      System.out.println("LDAP: " + filter);
      services = bundleContext.getServiceReferences(type.getName(), filter);
      if (null != services) {
        for (ServiceReference sr : services) {
          System.out.println("SREF:" + sr);
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); // FIXME?
      return new ArrayList<T>().iterator();
    }

    return new Iterator<T>() {
      int i = 0;

      public boolean hasNext() {
        return i < services.length;
      }

      public T next() {
        try {
          return type.cast(bundleContext.getService(services[i++]));
        } catch (Exception e) {
          e.printStackTrace();
          return null; // FIXME: provide Null object?
        }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
