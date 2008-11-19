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
import org.ops4j.peaberry.internal.ServiceBuilderImpl;
import org.ops4j.peaberry.osgi.OSGiServiceRegistry;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

/**
 * Guice extension that supports injection and outjection of dynamic services.
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
 * // the service is exported at injection time
 * bind(export(DictionaryService.class)).to(service(myDictionary).export());</pre>
 * 
 * Applying a custom filter to find a specific service:
 * 
 * <pre> service(DictionaryService.class).filter(ldap(&quot;(Language=French)&quot;)).single()</pre>
 * 
 * Applying custom attributes to an exported service:
 * 
 * <pre> service(myDictionary).attributes(names(&quot;Language=French&quot;)).export()</pre>
 * 
 * NOTE: helper methods for dealing with filters and attributes are in the
 * {@code org.ops4j.peaberry.util} package.
 * <p>
 * You can also decorate services with additional behaviour:
 * 
 * <pre> service(DictionaryService.class).decoratedWith(myDecorator).single()</pre>
 * 
 * or ask for the service to be injected directly, instead of using a dynamic proxy:
 * 
 * <pre> service(DictionaryService.class).single().direct())</pre>
 * 
 * similarly, if you don't want to bother with an Export handle when exporting:
 * 
 * <pre> service(myDictionary).export().direct()</pre>
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
   * Start building a dynamic service provider for the given key.
   * 
   * @param key binding key
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final Key<? extends T> key) {
    return new ServiceBuilderImpl<T>(key);
  }

  /**
   * Start building a dynamic service provider for the given type.
   * 
   * @param type binding type
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final TypeLiteral<? extends T> type) {
    return new ServiceBuilderImpl<T>(Key.get(type));
  }

  /**
   * Start building a dynamic service provider for the given class.
   * 
   * @param clazz binding class
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final Class<? extends T> clazz) {
    return new ServiceBuilderImpl<T>(Key.get(clazz));
  }

  /**
   * Start building a dynamic service provider for the given instance.
   * 
   * @param instance service instance
   * @return dynamic service builder
   */
  public static <S, T extends S> DecoratedServiceBuilder<S> service(final T instance) {
    return new ServiceBuilderImpl<S>(instance);
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
    return new AbstractModule() {

      @Override
      protected void configure() {
        bind(ServiceRegistry.class).to(OSGiServiceRegistry.class);
        bind(BundleContext.class).toInstance(bundleContext);
      }

      @Override
      public String toString() {
        return String.format("OSGiModule(%s)", bundleContext.getBundle());
      }
    };
  }
}
