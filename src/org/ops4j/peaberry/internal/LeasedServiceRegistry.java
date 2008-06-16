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

import org.ops4j.peaberry.ServiceRegistry;

/**
 * Wraps cache around an existing {@link ServiceRegistry} - assumes that only
 * one service query will be used with a given {@link LeasedServiceRegistry}.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class LeasedServiceRegistry
    implements ServiceRegistry {

  // configuration
  private final ServiceRegistry registry;
  private final long leaseMillis;

  // internal cache
  private Collection<?> services;
  private long expireMillis = 0L;

  public LeasedServiceRegistry(final ServiceRegistry registry, final int seconds) {
    this.registry = registry;
    this.leaseMillis = seconds * 1000;
  }

  /**
   * Uses a timed cache to decide when to delegate to the underlying registry.
   * 
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public synchronized <T> Iterator<T> lookup(final Class<? extends T> type, final String filter) {
    final long now = System.currentTimeMillis();

    // check rolling lease...
    if (expireMillis < now) {
      final Collection<T> freshServices = new ArrayList<T>();

      try {
        // deep cache, stopping at the first unavailable service
        for (final Iterator<T> i = registry.lookup(type, filter); i.hasNext();) {
          freshServices.add(i.next());
        }
      } catch (final Exception e) {}

      // never cache empty results
      if (freshServices.size() == 0) {
        return freshServices.iterator();
      }

      services = freshServices;
    }

    // negative lease times are treated as 'forever', i.e. static lookup
    expireMillis = leaseMillis < 0 ? Long.MAX_VALUE : (now + leaseMillis);

    return (Iterator<T>) services.iterator();
  }

  // /CLOVER:OFF
  public <T, S extends T> Handle<T> add(final S service, final Map<String, ?> attributes) {
    return registry.add((T) service, attributes);
  } // /CLOVER:ON

  @Override
  public String toString() {
    final String lease = leaseMillis < 0 ? "STATIC" : leaseMillis + "ms";
    return String.format("%s[%s]", registry, lease);
  }
}
