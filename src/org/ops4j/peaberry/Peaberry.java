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

import static com.google.inject.internal.Objects.nonNull;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.member;
import static org.ops4j.peaberry.internal.ServiceProviderFactory.getServiceProvider;

import org.ops4j.peaberry.internal.NonDelegatingClassLoaderHook;
import org.ops4j.peaberry.internal.OSGiServiceRegistry;
import org.ops4j.peaberry.internal.ServiceBindingFactory;
import org.osgi.framework.BundleContext;

import com.google.inject.Binder;
import com.google.inject.BindingFactory;
import com.google.inject.ClassLoaderHook;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.GuiceCodeGen;

/**
 * Guice extension that supports injection of dynamic services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class Peaberry {

  // instances not allowed
  private Peaberry() {}

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured to find all services compatible with the target.
   * 
   * @param registry dynamic service registry
   * @param target current binding target
   * 
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      Class<? extends T> target) {

    return getServiceProvider(registry, Key.get(target));
  }

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured to find all services compatible with the target.
   * 
   * @param registry dynamic service registry
   * @param target current binding target
   * 
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      TypeLiteral<? extends T> target) {

    return getServiceProvider(registry, Key.get(target));
  }

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured with the custom {@link Service} specification.
   * 
   * @param registry dynamic service registry
   * @param target current binding target
   * @param spec custom service specification
   * 
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      Class<? extends T> target, Service spec) {

    return getServiceProvider(registry, Key.get(target, spec));
  }

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured with the custom {@link Service} specification.
   * 
   * @param registry dynamic service registry
   * @param target current binding target
   * @param spec custom service specification
   * 
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      TypeLiteral<? extends T> target, Service spec) {

    return getServiceProvider(registry, Key.get(target, spec));
  }

  /**
   * Creates a new OSGi {@link ServiceRegistry} adaptor for the given bundle.
   * 
   * @param bundleContext current bundle context
   * 
   * @return OSGi specific {@link ServiceRegistry}
   */
  public static ServiceRegistry osgiServiceRegistry(BundleContext bundleContext) {

    nonNull(bundleContext, "bundle context");

    return new OSGiServiceRegistry(bundleContext);
  }

  /**
   * Shared Guice classloader hook for use with OSGi frameworks
   */
  private static final ClassLoaderHook OSGI_CLASSLOADER_HOOK =
      new NonDelegatingClassLoaderHook();

  /**
   * Enable support for using Guice in non-delegating containers, such as OSGi,
   * both in the current thread and any threads created by it after this point.
   */
  public static void nonDelegatingContainer() {
    GuiceCodeGen.setThreadClassLoaderHook(OSGI_CLASSLOADER_HOOK);
  }

  /**
   * Creates a Guice module for an OSGi bundle, with bindings to the given
   * {@link BundleContext} and OSGi {@link ServiceRegistry}, as well as a
   * {@link BindingFactory} that will automatically bind injection points
   * annotated with {@link Service} to matching OSGi services.
   * 
   * An OSGi classloader hook is also set for the thread using this module.
   * 
   * @param bundleContext current bundle context
   * 
   * @return OSGi service injection rules
   */
  public static Module osgiModule(final BundleContext bundleContext) {

    nonNull(bundleContext, "bundle context");

    return new Module() {
      public void configure(Binder binder) {
        nonDelegatingContainer();

        ServiceRegistry registry = osgiServiceRegistry(bundleContext);

        // useful bindings for OSGi applications
        binder.bind(BundleContext.class).toInstance(bundleContext);
        binder.bind(ServiceRegistry.class).toInstance(registry);

        // auto-bind service dependencies and implementations
        binder.addBindingFactory(member(annotatedWith(Service.class)),
            new ServiceBindingFactory(registry));
      }

      @Override
      public String toString() {
        return String.format("OSGiModule(%s)", bundleContext.getBundle());
      }
    };
  }
}
