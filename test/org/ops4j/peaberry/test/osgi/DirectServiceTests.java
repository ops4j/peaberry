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
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(testName = "DirectServiceTests", suiteName = "OSGi")
public final class DirectServiceTests {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    binder.bind(export(DummyService.class)).toProvider(
        registration(Key.get(DummyService.class, named("Backing"))).export());

    binder.bind(DummyService.class).annotatedWith(named("Service")).toProvider(
        service(DummyService.class).direct().single());

    binder.bind(iterable(DummyService.class)).annotatedWith(named("Service")).toProvider(
        service(DummyService.class).direct().multiple());

    binder.bind(DummyService.class).annotatedWith(named("Backing")).toProvider(
        DummyServiceProvider.class).in(Scopes.SINGLETON);

    binder.bind(Holder.class);
  }

  protected static interface DummyService {
    String test();
  }

  static class DummyServiceProvider
      implements Provider<DummyService> {

    public DummyService get() {
      return new DummyService() {
        public String test() {
          return "TEST";
        }
      };
    }
  }

  @Retention(RUNTIME)
  @interface Nullable {}

  static class Holder {
    @Inject
    @Named("Backing")
    public DummyService backing;

    @Inject
    @Nullable
    @Named("Service")
    public DummyService service;

    @Inject
    @Named("Service")
    public Iterable<DummyService> services;
  }

  @Inject
  Injector injector;

  @Inject
  Export<DummyService> exportedService;

  public void testWiring() {

    Holder holder = injector.getInstance(Holder.class);

    assert holder.backing == holder.service;
    assert holder.backing == holder.services.iterator().next();
    assert "TEST".equals(holder.service.test());
    assert "TEST".equals(holder.services.iterator().next().test());

    exportedService.remove();

    assert holder.backing == holder.service;
    assert holder.backing == holder.services.iterator().next();
    assert "TEST".equals(holder.service.test());
    assert "TEST".equals(holder.services.iterator().next().test());

    holder = injector.getInstance(Holder.class);

    assert holder.backing != holder.service;
    assert null == holder.service;
    assert false == holder.services.iterator().hasNext();
  }
}
