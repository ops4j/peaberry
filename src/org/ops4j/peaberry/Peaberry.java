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
 * For example, injecting a stock quote service:
 * 
 * <pre> {@literal @}Inject
 * StockQuote quote;
 * ...
 * bind(StockQuote.class).to(service(StockQuote.class).single());</pre>
 * 
 * Injecting many stock quote services:
 * 
 * <pre> {@literal @}Inject
 * Iterable&lt;StockQuote&gt; quotes;
 * ...
 * bind(iterable(StockQuote.class)).to(service(StockQuote.class).multiple());</pre>
 * 
 * Exporting an implementation as a stock quote service:
 * 
 * <pre> {@literal @}Inject
 * // the service can be controlled by the Export handle
 * Export&lt;StockQuote&gt; exportedQuote;
 * ...
 * // the service is exported at injection time
 * bind(export(StockQuote.class)).to(service(myQuoteImpl).export());</pre>
 * 
 * Applying a custom filter to find a specific service:
 * 
 * <pre> service(StockQuote.class).filter(ldap(&quot;(Currency=GBP)&quot;)).single()</pre>
 * 
 * Applying custom attributes to an exported service:
 * 
 * <pre> service(myQuoteImpl).attributes(names(&quot;Currency=GBP&quot;)).export()</pre>
 * 
 * (the ldap and names utility methods are from {@code org.ops4j.peaberry.util})
 * <p>
 * You can also decorate services with additional behaviour:
 * 
 * <pre> service(StockQuote.class).decoratedWith(someDecoratorImpl).single()</pre>
 * 
 * or ask for them to be injected directly, instead of using a dynamic proxy:
 * 
 * <pre> service(StockQuote.class).single().direct()</pre>
 * 
 * similarly, if you don't want to bother with an Export handle when exporting:
 * 
 * <pre> service(myQuoteImpl).export().direct()</pre>
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
  public static <T> DecoratedServiceBuilder<T> service(final Key<T> key) {
    return new ServiceBuilderImpl<T>(key);
  }

  /**
   * Start building a dynamic service provider for the given type.
   * 
   * @param type binding type
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final TypeLiteral<T> type) {
    return new ServiceBuilderImpl<T>(Key.get(type));
  }

  /**
   * Start building a dynamic service provider for the given class.
   * 
   * @param clazz binding class
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final Class<T> clazz) {
    return new ServiceBuilderImpl<T>(Key.get(clazz));
  }

  /**
   * Start building a dynamic service provider for the given instance.
   * 
   * @param instance service instance
   * @return dynamic service builder
   */
  public static <T> DecoratedServiceBuilder<T> service(final T instance) {
    return new ServiceBuilderImpl<T>(instance);
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
