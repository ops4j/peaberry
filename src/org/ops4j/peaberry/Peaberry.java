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
 * Guice extension that supports injection and registration of dynamic services.
 * <p>
 * For example, injecting a dictionary service:
 * 
 * <pre> {@literal @}Inject
 * DictionaryService dictionaryService;
 * ...
 * bind(DictionaryService.class).to(service(DictionaryService.class).single());</pre>
 * 
 * Injecting many dictionary services:
 * 
 * <pre> {@literal @}Inject
 * Iterable&lt;DictionaryService&gt; dictionaryServices;
 * ...
 * bind(iterable(DictionaryService.class)).to(service(DictionaryService.class).multiple());</pre>
 * 
 * Exporting an implementation as a dictionary service:
 * 
 * <pre> {@literal @}Inject
 * // the service can be controlled by the Export handle
 * Export&lt;DictionaryService&gt; exportedDictionaryService;
 * ...
 * // the service is actually exported at injection time
 * bind(export(DictionaryService.class)).to(registration(dictionaryImplKey).export());</pre>
 * 
 * Applying a custom filter to find a specific service:
 * 
 * <pre> service(DictionaryService.class).filter(ldap(&quot;(Language=French)&quot;)).single();</pre>
 * 
 * Applying custom attributes to an exported service:
 * 
 * <pre> registration(dictionaryKey).attributes(names(&quot;Language=French&quot;)).export();</pre>
 * 
 * NOTE: helper methods for dealing with filters and attributes are in the
 * {@code org.ops4j.peaberry.util} package.
 * <p>
 * You can also decorate an imported service with additional behaviour:
 * 
 * <pre> service(DictionaryService.class).decoratedWith(decoratorImplKey).single();</pre>
 * 
 * or ask for the service to be injected directly, instead of a dynamic proxy:
 * 
 * <pre> service(DictionaryService.class).direct().single();</pre>
 * 
 * See the <a href="http://code.google.com/p/peaberry/wiki/UserGuide"
 * target="_blank">User Guide</a> for more examples.
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
   * Create a new OSGi {@code ServiceRegistry} for the given bundle context.
   * 
   * @param bundleContext current bundle context
   * @return OSGi specific {@code ServiceRegistry}
   */
  public static ServiceRegistry osgiServiceRegistry(final BundleContext bundleContext) {
    return new OSGiServiceRegistry(bundleContext);
  }

  /**
   * Create a new Guice module with bindings to the given bundle context and
   * OSGi {@code ServiceRegistry}.
   * 
   * @param bundleContext current bundle context
   * @return OSGi specific Guice bindings
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
