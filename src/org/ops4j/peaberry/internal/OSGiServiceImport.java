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

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * {@code Import} implementation based on an OSGi {@code ServiceReference}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceImport<T>
    implements Import<T> {

  private final BundleContext bundleContext;
  private final Class<? extends T> type;
  private final ServiceReference ref;

  public OSGiServiceImport(final BundleContext bundleContext, final Class<? extends T> type,
      final ServiceReference ref) {

    this.bundleContext = bundleContext;
    this.type = type;
    this.ref = ref;
  }

  public T get() {
    final T obj;
    try {
      obj = type.cast(bundleContext.getService(ref));
    } catch (final Exception e) {
      throw new ServiceUnavailableException(e);
    }
    if (null == obj) {
      throw new ServiceUnavailableException();
    }
    return obj;
  }

  public void unget() {
    try {
      bundleContext.ungetService(ref);
    } catch (final Exception e) {} // NOPMD
  }
}
