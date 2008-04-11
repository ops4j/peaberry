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
import java.util.Map;

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
  private final long leaseMillis;

  private Collection<?> services;
  private volatile Long expireMillis = 0L;

  public LeasedServiceRegistry(ServiceRegistry registry, Leased leased) {
    this.registry = registry;
    this.leaseMillis = leased.seconds() * 1000;
  }

  @SuppressWarnings("unchecked")
  public <T> Iterator<T> lookup(Class<? extends T> type, String filter) {
    final long now = System.currentTimeMillis();

    // double-checked locking is ok on Java 5 runtimes
    if (expireMillis < now) {
      synchronized (this) {
        // /CLOVER:OFF
        if (expireMillis < now) {
          // /CLOVER:ON

          Collection<T> freshServices = new ArrayList<T>();
          for (Iterator<T> i = registry.lookup(type, filter); i.hasNext();) {
            try {
              freshServices.add(i.next());
            } catch (Exception e) {}
          }

          // lease only starts when there are services
          if (freshServices.size() == 0) {
            return freshServices.iterator();
          } else {
            services = freshServices;
          }

          // negative lease times are treated as 'forever', ie. static lookup
          expireMillis = leaseMillis < 0 ? Long.MAX_VALUE : (now + leaseMillis);
        }
      }
    }

    return (Iterator<T>) services.iterator();
  }

  public <T> Handle<T> add(T service, Map<?, ?> properties) {
    return registry.add(service, properties);
  }

  @Override
  public String toString() {
    String lease = leaseMillis < 0 ? "STATIC" : leaseMillis + "ms";
    return String.format("%s[%s]", registry, lease);
  }
}
