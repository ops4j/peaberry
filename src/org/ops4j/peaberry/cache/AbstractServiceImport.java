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

package org.ops4j.peaberry.cache;

import static java.util.logging.Level.WARNING;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * Partial {@link Import} implementation with generation based caching.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public abstract class AbstractServiceImport<T>
    implements Import<T>, Comparable<Import<T>> {

  private static final Logger LOGGER = Logger.getLogger(AbstractServiceImport.class.getName());

  static final int MODIFIED = 0;
  static final int UNREGISTERING = 1;

  private static final int INVALID = -1;
  private static final int DORMANT = 0;
  private static final int ACTIVE = 1;

  // generation-based service cache
  private volatile T instance;
  private final AtomicInteger count;
  private volatile int state;
  private int generation;

  private final List<Export<?>> watchers;

  // current cache generation (global member)
  private static volatile int cacheGeneration;

  protected AbstractServiceImport() {

    // need accurate usage count
    count = new AtomicInteger();

    watchers = new ArrayList<Export<?>>(2);
  }

  public final T get() {
    count.getAndIncrement();
    if (DORMANT == state) {
      synchronized (this) {
        if (DORMANT == state) {
          try {
            instance = acquireService();
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

  public final void unget() {
    generation = cacheGeneration;
    count.decrementAndGet();
  }

  public final boolean available() {
    return INVALID != state;
  }

  /**
   * Protected from concurrent access by {@link AbstractServiceListener}.
   */
  void addWatcher(final Export<?> export) {
    watchers.add(export);
  }

  /**
   * Protected from concurrent access by {@link AbstractServiceListener}.
   */
  void invalidate() {
    notifyWatchers(UNREGISTERING);
    watchers.clear();
    synchronized (this) {
      instance = null;
      state = INVALID;
    }
  }

  public static final void setCacheGeneration(final int newGeneration) {
    cacheGeneration = newGeneration;
  }

  /**
   * Protected from concurrent access by {@link AbstractServiceListener}.
   */
  void flush(final int targetGeneration) {

    // check no-one is using the active service and it belongs to the generation
    if (targetGeneration == generation && ACTIVE == state && 0 == count.get()) {
      synchronized (this) {

        // has it just gone?
        if (INVALID == state) {
          return;
        }

        // block other threads entering get()
        state = DORMANT;

        if (count.get() > 0) {
          state = ACTIVE; // another thread snuck in, so roll back...
        } else {
          try {
            releaseService(instance);
          } catch (final RuntimeException re) {/* already gone */} // NOPMD
          finally {
            instance = null;
          }
        }
      }
    }
  }

  void notifyWatchers(final int eventType) {
    for (final Export<?> export : watchers) {
      try {
        switch (eventType) {
        case MODIFIED:
          export.attributes(attributes());
          break;
        case UNREGISTERING:
          export.unput();
          break;
        default:
          break;
        }
      } catch (final RuntimeException re) {
        LOGGER.log(WARNING, "Exception in service watcher", re);
      }
    }
  }

  protected abstract T acquireService();

  protected abstract boolean hasRankingChanged();

  protected abstract void releaseService(T o);
}
