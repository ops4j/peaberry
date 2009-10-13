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

import java.util.Map;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.cache.AbstractServiceImport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * {@link Import} implementation backed by an OSGi {@link ServiceReference}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceImport<T>
    extends AbstractServiceImport<T> {

  private final BundleContext bundleContext;
  private final ServiceReference ref;

  // heavily used attributes
  private final long id;
  private int rank;

  private final Map<String, ?> attributes;

  OSGiServiceImport(final BundleContext bundleContext, final ServiceReference ref) {
    this.bundleContext = bundleContext;
    this.ref = ref;

    // cache attributes used when sorting services
    id = getNumberProperty(SERVICE_ID).longValue();
    rank = getNumberProperty(SERVICE_RANKING).intValue();

    attributes = new OSGiServiceAttributes(ref);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected T acquireService() {
    return (T) bundleContext.getService(ref);
  }

  public Map<String, ?> attributes() {
    return attributes;
  }

  @Override
  protected boolean hasRankingChanged() {
    // ranking is mutable...
    final int oldRank = rank;
    rank = getNumberProperty(SERVICE_RANKING).intValue();
    return oldRank != rank;
  }

  @Override
  protected void releaseService(final T o) {
    bundleContext.ungetService(ref);
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof OSGiServiceImport<?>) {
      // service id is a unique identifier
      return id == ((OSGiServiceImport<?>) rhs).id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ id >>> 32);
  }

  public int compareTo(final Import<T> rhs) {
    final OSGiServiceImport<T> rhsImport = (OSGiServiceImport<T>) rhs;

    if (id == rhsImport.id) {
      return 0;
    }

    if (rank == rhsImport.rank) {
      // prefer lower service id
      return id < rhsImport.id ? -1 : 1;
    }

    // but higher ranking beats all
    return rank > rhsImport.rank ? -1 : 1;
  }

  private Number getNumberProperty(final String key) {
    final Object num = ref.getProperty(key);
    return num instanceof Number ? (Number) num : 0;
  }
}
