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

package org.ops4j.peaberry;

import org.ops4j.peaberry.builders.DecoratedServiceBuilder;
import org.ops4j.peaberry.builders.QualifiedRegistrationBuilder;
import org.ops4j.peaberry.internal.DecoratedServiceBuilderImpl;
import org.ops4j.peaberry.internal.OSGiServiceRegistry;
import org.ops4j.peaberry.internal.QualifiedRegistrationBuilderImpl;
import org.osgi.framework.BundleContext;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;

/**
 * Guice extension that supports injection of dynamic services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Peaberry {

  // instances not allowed
  private Peaberry() {}

  /**
   * Start building a dynamic service provider for the given interface.
   * 
   * @param clazz service interface
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final Class<? extends T> clazz) {
    return new DecoratedServiceBuilderImpl<T>(clazz);
  }

  /**
   * Start building a service registration for the given implementation key.
   * 
   * @param key implementation key
   * @return service registration builder
   */
  public static <T> QualifiedRegistrationBuilder<T> registration(final Key<? extends T> key) {
    return new QualifiedRegistrationBuilderImpl<T>(key);
  }

  /**
   * Creates a new OSGi {@link ServiceRegistry} adaptor for the given bundle.
   * 
   * @param bundleContext current bundle context
   * @return OSGi specific {@link ServiceRegistry}
   */
  public static ServiceRegistry osgiServiceRegistry(final BundleContext bundleContext) {
    return new OSGiServiceRegistry(bundleContext);
  }

  /**
   * Creates a Guice module for an OSGi bundle, with bindings to the OSGi
   * specific {@link ServiceRegistry} and current {@link BundleContext}.
   * 
   * @param bundleContext current bundle context
   * @return OSGi bindings
   */
  public static Module osgiModule(final BundleContext bundleContext) {

    return new Module() {
      public void configure(final Binder binder) {
        binder.bind(ServiceRegistry.class).to(OSGiServiceRegistry.class);
        binder.bind(BundleContext.class).toInstance(bundleContext);
      }

      @Override
      public String toString() {
        return String.format("OSGiModule(%s)", bundleContext.getBundle());
      }
    };
  }
}
