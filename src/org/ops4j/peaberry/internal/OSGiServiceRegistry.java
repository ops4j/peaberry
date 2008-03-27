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

import java.util.Collections;
import java.util.Iterator;

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
  public Iterator<?> lookup(String query) {

    final ServiceReference[] services;

    try {
      System.out.println("LDAP: " + query);
      services = bundleContext.getServiceReferences(null, query);
      if (null != services) {
        for (ServiceReference sr : services) {
          System.out.println("SR:" + sr);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.EMPTY_LIST.iterator();
    }

    return new Iterator<Object>() {
      int i = 0;

      public boolean hasNext() {
        return i < services.length;
      }

      public Object next() {
        try {
          return bundleContext.getService(services[i++]);
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
