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
import org.ops4j.peaberry.util.SimpleExport;

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
      extends SimpleExport<T> {

    private Export<T> realExport;

    FilteredExport(final Import<T> service) {
      super(service);

      checkMatchingService();
    }

    private void checkMatchingService() {
      if (filter.matches(super.attributes())) {
        if (null == realExport) {
          // service metadata now matches
          realExport = watcher.add(this);
        }
      } else if (null != realExport) {
        // metadata doesn't match anymore!
        final Export<T> temp = realExport;
        realExport = null;
        temp.unput();
      }
    }

    // Export aspect is only active when service matches filter

    @Override
    public synchronized void put(final T instance) {
      super.put(instance);

      if (null != realExport) {
        realExport.put(instance);
      }
    }

    @Override
    public synchronized void attributes(final Map<String, ?> newAttributes) {
      super.attributes(newAttributes);

      if (null != realExport) {
        realExport.attributes(newAttributes);
      }

      checkMatchingService(); // re-check filter against latest attributes
    }

    @Override
    public synchronized void unput() {
      super.unput();

      if (null != realExport) {
        realExport.unput();
      }
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
