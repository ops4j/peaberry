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

import java.lang.annotation.Annotation;

import org.ops4j.peaberry.internal.LeasedScope;
import org.ops4j.peaberry.internal.NonDelegatingClassLoaderHook;
import org.ops4j.peaberry.internal.OSGiServiceRegistry;
import org.ops4j.peaberry.internal.ServiceBindingFactory;
import org.ops4j.peaberry.internal.ServiceProviderFactory;
import org.ops4j.peaberry.internal.StaticScope;
import org.osgi.framework.BundleContext;

import com.google.inject.Binder;
import com.google.inject.ClassLoaderHook;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.GuiceCodeGen;

/**
 * Guice extension that supports injection of dynamic services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class Peaberry {

  private static final Scope STATIC_SERVICE_SCOPE = new StaticScope();
  private static final Scope LEASED_SERVICE_SCOPE =
      new LeasedScope(Integer.getInteger("peaberry.default.lease", 300));

  private static final ClassLoaderHook OSGI_CLASSLOADER_HOOK =
      new NonDelegatingClassLoaderHook();

  // instances not allowed
  private Peaberry() {}

  /**
   * Create a {@link Service} specification on-the-fly.
   * 
   * @param filter RFC-1960 (LDAP) filter
   * @param interfaces custom service API
   * @return {@link Service} specification
   */
  public Service service(final String filter, final Class<?>... interfaces) {
    return new Service() {

      public Class<?>[] interfaces() {
        return interfaces;
      }

      public String value() {
        return filter;
      }

      public Class<? extends Annotation> annotationType() {
        return Service.class;
      }
    };
  }

  /**
   * Creates a dynamic service provider for the given {@link ServiceRegistry}.
   * The provider is configured to use the {@link Service} API and LDAP filter.
   * 
   * @param registry dynamic service registry
   * @param target literal type of the target
   * @param spec optional service specification
   * @return dynamic service provider
   */
  public static <T> Provider<T> serviceProvider(ServiceRegistry registry,
      TypeLiteral<T> target, Service spec) {

    return ServiceProviderFactory.getServiceProvider(registry, target, spec);
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

  /**
   * @param bundleContext current bundle context
   * @return OSGi service injection rules
   */
  public static Module getBundleModule(final BundleContext bundleContext) {

    return new Module() {
      public void configure(Binder binder) {

        binder.bind(BundleContext.class).toInstance(bundleContext);

        binder.bindScope(Static.class, STATIC_SERVICE_SCOPE);
        binder.bindScope(Leased.class, LEASED_SERVICE_SCOPE);

        // auto-bind service dependencies and implementations
        binder.addBindingFactory(member(annotatedWithService()),
            new ServiceBindingFactory(getOSGiServiceRegistry(bundleContext)));

        GuiceCodeGen.setThreadClassLoaderHook(OSGI_CLASSLOADER_HOOK);
      }
    };
  }
}
