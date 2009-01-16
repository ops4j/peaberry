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

import static org.osgi.framework.Constants.SERVICE_ID;
import static org.osgi.framework.Constants.SERVICE_RANKING;
import static org.osgi.framework.ServiceEvent.MODIFIED;
import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * {@link Import} implementation backed by an OSGi {@link ServiceReference}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class OSGiServiceImport
    implements Import<Object>, Comparable<OSGiServiceImport> {

  private static final int INVALID = -1;
  private static final int DORMANT = 0;
  private static final int ACTIVE = 1;

  private final BundleContext bundleContext;
  private final ServiceReference ref;

  // heavily used attributes
  private final long id;
  private int rank;

  // generation-based service cache
  private final AtomicInteger count;
  private volatile int state;
  private Object instance;
  private int generation;

  private final Map<String, ?> attributes;
  private final List<Export<?>> watchers;

  // current cache generation (global member)
  private static volatile int cacheGeneration;

  public OSGiServiceImport(final BundleContext bundleContext, final ServiceReference ref) {
    this.bundleContext = bundleContext;
    this.ref = ref;

    // cache attributes used when sorting services
    id = getNumberProperty(SERVICE_ID).longValue();
    rank = getNumberProperty(SERVICE_RANKING).intValue();

    // need accurate usage count
    count = new AtomicInteger();

    attributes = new OSGiServiceAttributes(ref);
    watchers = new ArrayList<Export<?>>(2);
  }

  /**
   * Protected from concurrent access by {@link OSGiServiceListener}.
   */
  public boolean updateRanking() {
    notifyWatchers(MODIFIED);

    // ranking is mutable...
    final int oldRank = rank;
    rank = getNumberProperty(SERVICE_RANKING).intValue();
    return oldRank != rank;
  }

  public boolean matches(final AttributeFilter filter) {
    return filter.matches(attributes);
  }

  public Object get() {
    count.getAndIncrement();
    if (DORMANT == state) {
      synchronized (this) {
        if (DORMANT == state) {
          try {
            instance = bundleContext.getService(ref);
          } catch (final RuntimeException re) {
            throw new ServiceUnavailableException(re);
          } finally {
            state = ACTIVE;
          }
        }
      }
    }
    return instance;
  }

  public Map<String, ?> attributes() {
    return INVALID == state ? null : attributes;
  }

  public void unget() {
    generation = cacheGeneration;
    count.decrementAndGet();
  }

  /**
   * Protected from concurrent access by {@link OSGiServiceListener}.
   */
  public void addWatcher(final Export<?> export) {
    watchers.add(export);
  }

  /**
   * Protected from concurrent access by {@link OSGiServiceListener}.
   */
  public void invalidate() {
    notifyWatchers(UNREGISTERING);
    watchers.clear();

    instance = null;
    state = INVALID; // force memory flush
  }

  public static void setCacheGeneration(final int newGeneration) {
    cacheGeneration = newGeneration;
  }

  /**
   * Protected from concurrent access by {@link OSGiServiceListener}.
   */
  public void flush(final int targetGeneration) {

    // check no-one is using the service and it belongs to the target generation
    if (targetGeneration == generation && ACTIVE == state && 0 == count.get()) {
      synchronized (this) {

        // block other threads entering get()
        state = DORMANT;

        if (count.get() > 0) {
          state = ACTIVE; // another thread snuck in, so roll back...
        } else {
          instance = null;
          try {
            // cached result not being used
            bundleContext.ungetService(ref);
          } catch (final RuntimeException re) {/* already gone */} // NOPMD
        }
      }
    }
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof OSGiServiceImport) {
      // service id is a unique identifier
      return id == ((OSGiServiceImport) rhs).id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ id >>> 32);
  }

  public int compareTo(final OSGiServiceImport rhs) {

    if (id == rhs.id) {
      return 0;
    }

    if (rank == rhs.rank) {
      // prefer lower service id
      return id < rhs.id ? -1 : 1;
    }

    // but higher ranking beats all
    return rank > rhs.rank ? -1 : 1;
  }

  private Number getNumberProperty(final String key) {
    final Object num = ref.getProperty(key);
    return num instanceof Number ? (Number) num : 0;
  }

  private void notifyWatchers(final int eventType) {
    for (final Export<?> export : watchers) {
      try {
        switch (eventType) {
        case MODIFIED:
          export.attributes(attributes);
          break;
        case UNREGISTERING:
          export.unput();
          break;
        default:
          break;
        }
      } catch (final RuntimeException re) {/* ignore */} // NOPMD
    }
  }
}
