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

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceWatcher;

/**
 * Pre-filtered {@link ServiceWatcher} that handles mutable services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class FilteredServiceWatcher<S>
    implements ServiceWatcher<S> {

  final AttributeFilter filter;
  final ServiceWatcher<S> watcher;

  FilteredServiceWatcher(final AttributeFilter filter, final ServiceWatcher<S> watcher) {
    this.filter = filter;
    this.watcher = watcher;
  }

  public <T extends S> Export<T> add(final Import<T> service) {
    // service metadata can change, so must be able to recheck
    return new FilteredExport<T>(service);
  }

  private final class FilteredExport<T extends S>
      implements Export<T> {

    private final Import<T> service;
    private Export<T> realExport;

    FilteredExport(final Import<T> service) {
      this.service = service;
      checkMatchingService();
    }

    private synchronized void checkMatchingService() {
      if (((OSGiServiceImport) (Import<?>) service).matches(filter)) {
        if (null == realExport) {
          // service metadata now matches
          realExport = watcher.add(service);
        }
      } else if (null != realExport) {
        // metadata doesn't match anymore
        final Export<T> temp = realExport;
        realExport = null;
        temp.unput();
      }
    }

    // Export aspect is only active when service matches filter

    public void put(final T instance) {
      // this method will never actually get called for an OSGi service
      throw new UnsupportedOperationException();
    }

    public synchronized void attributes(final Map<String, ?> attributes) {
      if (null != realExport) {
        realExport.attributes(attributes);
      }
      checkMatchingService(); // can safely assume OSGi service is alive
    }

    public synchronized void unput() {
      if (null != realExport) {
        realExport.unput(); // last method to be called for an OSGi service
      }
    }

    // Import aspect is always available

    public T get() {
      return service.get();
    }

    public Map<String, ?> attributes() {
      return service.attributes();
    }

    public void unget() {
      service.unget();
    }
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof FilteredServiceWatcher) {
      final FilteredServiceWatcher<?> filteredWatcher = (FilteredServiceWatcher<?>) rhs;
      return filter.equals(filteredWatcher.filter) && watcher.equals(filteredWatcher.watcher);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return filter.hashCode() ^ watcher.hashCode();
  }
}
