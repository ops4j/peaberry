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

package org.ops4j.peaberry.test.osgi;

import static com.google.inject.name.Names.named;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import java.lang.annotation.Retention;

import org.ops4j.peaberry.Export;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.name.Named;

/**
 * Test direct static services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "DirectServiceTests", suiteName = "OSGi")
public class DirectServiceTests
    extends OSGiServiceTester {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    binder.bind(export(SimpleService.class)).toProvider(
        registration(Key.get(SimpleService.class, named("Backing"))).export());

    binder.bind(SimpleService.class).annotatedWith(named("Service")).toProvider(
        service(SimpleService.class).direct().single());

    binder.bind(iterable(SimpleService.class)).annotatedWith(named("Service")).toProvider(
        service(SimpleService.class).direct().multiple());

    binder.bind(SimpleService.class).annotatedWith(named("Backing")).toProvider(
        SimpleServiceProvider.class).in(Scopes.SINGLETON);

    binder.bind(Holder.class);
  }

  static class SimpleServiceProvider
      implements Provider<SimpleService> {

    public SimpleService get() {
      return new SimpleService() {
        public String check() {
          return "TEST";
        }
      };
    }
  }

  @Retention(RUNTIME)
  public @interface Nullable {}

  static class Holder {
    @Inject
    @Named("Backing")
    public SimpleService backing;

    @Inject
    @Nullable
    @Named("Service")
    public SimpleService service;

    @Inject
    @Named("Service")
    public Iterable<SimpleService> services;
  }

  @Inject
  Injector injector;

  @Inject
  Export<SimpleService> exportedService;

  public void testWiring() {

    Holder holder = injector.getInstance(Holder.class);

    assert holder.backing == holder.service;
    assert holder.backing == holder.services.iterator().next();
    checkService(holder.service, "TEST");
    checkServices(holder.services, "TEST");

    exportedService.remove();

    assert holder.backing == holder.service;
    assert holder.backing == holder.services.iterator().next();
    checkService(holder.service, "TEST");
    checkServices(holder.services, "TEST");

    holder = injector.getInstance(Holder.class);

    assert holder.backing != holder.service;
    assert null == holder.service;
    assert false == holder.services.iterator().hasNext();
    checkServices(holder.services);
  }
}
