/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.eclipse;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceWatcher;

/**
 * Pre-filtered {@link ServiceWatcher} for immutable Eclipse extensions.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class FilteredExtensionWatcher<S>
    implements ServiceWatcher<S> {

  private final AttributeFilter filter;
  private final ServiceWatcher<S> watcher;

  FilteredExtensionWatcher(final AttributeFilter filter, final ServiceWatcher<S> watcher) {
    this.filter = filter;
    this.watcher = watcher;
  }

  public <T extends S> Export<T> add(final Import<T> service) {
    return filter.matches(service.attributes()) ? watcher.add(service) : null;
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof FilteredExtensionWatcher) {
      final FilteredExtensionWatcher<?> filteredWatcher = (FilteredExtensionWatcher<?>) rhs;
      return filter.equals(filteredWatcher.filter) && watcher.equals(filteredWatcher.watcher);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return filter.hashCode() ^ watcher.hashCode();
  }
}
