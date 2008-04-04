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

import static com.google.inject.matcher.Matchers.member;
import static org.ops4j.peaberry.internal.ServiceMatcher.annotatedWithService;
import static org.ops4j.peaberry.internal.ServiceProviderFactory.getServiceProvider;

import java.lang.annotation.Annotation;

import org.ops4j.peaberry.internal.NonDelegatingClassLoaderHook;
import org.ops4j.peaberry.internal.OSGiServiceRegistry;
import org.ops4j.peaberry.internal.ServiceBindingFactory;
import org.osgi.framework.BundleContext;

import com.google.inject.Binder;
import com.google.inject.ClassLoaderHook;
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
   * Create a {@link Service} specification on-the-fly.
   * 
   * @param filter RFC-1960 (LDAP) filter
   * @param interfaces custom service API
   * @return {@link Service} specification
   */
  public static Service service(final String filter,
      final Class<?>... interfaces) {
    return new Service() {

      public Class<?>[] interfaces() {
        return interfaces;
      }

      public String value() {
        return null == filter ? "" : filter;
      }

      public Class<? extends Annotation> annotationType() {
        return Service.class;
      }
    };
  }

  /**
   * Create a {@link Leased} setting on-the-fly.
   * 
   * @param seconds lease time in seconds
   * @return {@link Leased} setting
   */
  public static Leased leased(final int seconds) {
    return new Leased() {

      public int seconds() {
        return seconds;
      }

      public Class<? extends Annotation> annotationType() {
        return Leased.class;
      }
    };
  }

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured with the {@link Service} specification and is
   * optionally {@link Leased}.
   * 
   * @param registry dynamic service registry
   * @param target literal type of the target
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      TypeLiteral<T> target) {

    return getServiceProvider(registry, target, null, null);
  }

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured with the {@link Service} specification and is
   * optionally {@link Leased}.
   * 
   * @param registry dynamic service registry
   * @param target literal type of the target
   * @param spec custom service specification
   * @param leased optionally leased
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      TypeLiteral<T> target, Service spec, Leased leased) {

    return getServiceProvider(registry, target, spec, leased);
  }

  /**
   * Create a new OSGi {@link ServiceRegistry} adaptor for the given bundle.
   * 
   * @param bundleContext current bundle context
   * @return OSGi specific {@link ServiceRegistry}
   */
  public static ServiceRegistry getOSGiServiceRegistry(
      BundleContext bundleContext) {

    return new OSGiServiceRegistry(bundleContext);
  }

  private static final ClassLoaderHook OSGI_CLASSLOADER_HOOK =
      new NonDelegatingClassLoaderHook();

  /**
   * Enable support for non-delegating containers, such as OSGi
   */
  public static void nonDelegatingContainer() {
    GuiceCodeGen.setThreadClassLoaderHook(OSGI_CLASSLOADER_HOOK);
  }

  /**
   * @param bundleContext current bundle context
   * @return OSGi service injection rules
   */
  public static Module getBundleModule(final BundleContext bundleContext) {

    return new Module() {
      public void configure(Binder binder) {

        binder.bind(BundleContext.class).toInstance(bundleContext);

        // auto-bind service dependencies and implementations
        binder.addBindingFactory(member(annotatedWithService()),
            new ServiceBindingFactory(getOSGiServiceRegistry(bundleContext)));

        nonDelegatingContainer();
      }
    };
  }
}
