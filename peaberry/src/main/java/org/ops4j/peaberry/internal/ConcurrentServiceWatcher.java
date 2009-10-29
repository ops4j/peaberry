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

import java.util.Iterator;
import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.util.SimpleExport;

/**
 * A {@link ServiceWatcher} with similar behaviour to {@link ConcurrentImport}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ConcurrentServiceWatcher<S>
    implements ServiceWatcher<S> {

  final Iterable<Import<S>> services;
  final ServiceWatcher<? super S> watcher;

  // the "best" service
  Import<S> currentImport;
  Export<S> currentExport;

  ConcurrentServiceWatcher(final Iterable<Import<S>> services,
      final ServiceWatcher<? super S> watcher) {
    this.services = services;
    this.watcher = watcher;
  }

  private final class TrackingExport<T>
      extends SimpleExport<T> {

    private final Import<T> thisImport;

    TrackingExport(final Import<T> thisImport) {
      super(thisImport);

      this.thisImport = thisImport;
    }

    // Export aspect is only active when our service is "best"

    @Override
    @SuppressWarnings("unchecked")
    public void put(final T newInstance) {
      super.put(newInstance);

      synchronized (ConcurrentServiceWatcher.this) {
        if (null != currentExport && thisImport.equals(currentImport)) {
          currentExport.put((S) newInstance);

          // is this being removed?
          if (null == newInstance) {
            updateBestService();
          }
        }
      }
    }

    @Override
    public void attributes(final Map<String, ?> newAttributes) {
      super.attributes(newAttributes);

      synchronized (ConcurrentServiceWatcher.this) {
        if (null != currentExport && thisImport.equals(currentImport)) {
          currentExport.attributes(newAttributes);
        }

        // has ranking changed?
        updateBestService();
      }
    }
  }

  public synchronized <T extends S> Export<T> add(final Import<T> service) {
    updateBestService();

    // every new service has a tracker
    return new TrackingExport<T>(service);
  }

  void updateBestService() {

    // check the last-known best service is still the best
    final Iterator<Import<S>> i = services.iterator();
    final Import<S> bestImport = i.hasNext() ? i.next() : null;

    if (null != currentImport && currentImport.equals(bestImport)) {
      return; // still the same...
    }

    // best service has changed
    if (null != currentExport) {
      currentExport.unput();
    }

    // report service (if any)
    currentImport = bestImport;
    currentExport = null == bestImport ? null : watcher.add(bestImport);
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof ConcurrentServiceWatcher<?>) {
      final ConcurrentServiceWatcher<?> concurrentWatcher = (ConcurrentServiceWatcher<?>) rhs;
      return services.equals(concurrentWatcher.services)
          && watcher.equals(concurrentWatcher.watcher);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return services.hashCode() ^ watcher.hashCode();
  }
}
