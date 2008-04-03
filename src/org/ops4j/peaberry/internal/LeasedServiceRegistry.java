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
import java.util.Collection;
import java.util.Iterator;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.ServiceRegistry;

/**
 * Provides a {@link Leased} {@link ServiceRegistry}.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class LeasedServiceRegistry
    implements ServiceRegistry {

  private final ServiceRegistry registry;
  private final int leaseInMillis;

  private Collection<Object> services;

  private volatile Long expireTimeInMillis = 0L;

  public LeasedServiceRegistry(ServiceRegistry registry, Leased leased) {
    this.registry = registry;
    this.leaseInMillis = leased.seconds() * 1000;
  }

  @SuppressWarnings("unchecked")
  public Iterator lookup(Class type, String filter) {

    if (expireTimeInMillis < System.currentTimeMillis()) {
      synchronized (this) {
        if (expireTimeInMillis < System.currentTimeMillis()) {

          Collection freshServices = new ArrayList();
          for (Iterator i = registry.lookup(type, filter); i.hasNext();) {
            freshServices.add(i.next());
          }

          if (freshServices.size() == 0) {
            return freshServices.iterator();
          } else {
            services = freshServices;
          }

          expireTimeInMillis = System.currentTimeMillis() + leaseInMillis;
        }
      }
    }

    return services.iterator();
  }
}
