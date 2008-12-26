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

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceScope;

/**
 * Pre-filtered {@link ServiceScope}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class FilteredServiceScope<S>
    implements ServiceScope<S> {

  final AttributeFilter filter;
  final ServiceScope<S> scope;

  public FilteredServiceScope(final AttributeFilter filter, final ServiceScope<S> scope) {
    this.filter = filter;
    this.scope = scope;
  }

  public <T extends S> Export<T> add(final Import<T> service) {
    if (null == filter || ((OSGiServiceImport) (Import<?>) service).matches(filter)) {
      return scope.add(service);
    }
    return null;
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof FilteredServiceScope) {
      final FilteredServiceScope<?> filteredScope = (FilteredServiceScope<?>) rhs;
      return equals(filter, filteredScope.filter) && equals(scope, filteredScope.scope);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (null == filter ? 0 : filter.hashCode()) ^ (null == scope ? 0 : scope.hashCode());
  }

  private static boolean equals(final Object lhs, final Object rhs) {
    return null == lhs ? null == rhs : lhs.equals(rhs);
  }
}
