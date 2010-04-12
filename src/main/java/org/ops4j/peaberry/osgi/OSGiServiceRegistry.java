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

import static org.ops4j.peaberry.Peaberry.NATIVE_FILTER_HINT;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.cache.AbstractServiceListener;
import org.ops4j.peaberry.cache.AbstractServiceRegistry;
import org.osgi.framework.BundleContext;

/**
 * OSGi {@link ServiceRegistry} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class OSGiServiceRegistry
    extends AbstractServiceRegistry {

  private final BundleContext bundleContext;

  @Inject
  public OSGiServiceRegistry(final BundleContext bundleContext) {
    super(Boolean.parseBoolean(bundleContext.getProperty(NATIVE_FILTER_HINT)));
    this.bundleContext = bundleContext;
  }

  @Override
  public final String toString() {
    return String.format("OSGiServiceRegistry[%s]", bundleContext.getBundle());
  }

  @Override
  public final int hashCode() {
    return bundleContext.hashCode();
  }

  @Override
  public final boolean equals(final Object rhs) {
    if (rhs instanceof OSGiServiceRegistry) {
      return bundleContext.equals(((OSGiServiceRegistry) rhs).bundleContext);
    }
    return false;
  }

  @Override
  protected final <T> AbstractServiceListener<T> createListener(final String ldapFilter) {
    return new OSGiServiceListener<T>(bundleContext, ldapFilter);
  }

  public final <T> Export<T> add(final Import<T> service) {
    // avoid cycles by ignoring our own services
    if (service instanceof OSGiServiceExport<?>) {
      return null;
    }
    return new OSGiServiceExport<T>(bundleContext, service);
  }
}
