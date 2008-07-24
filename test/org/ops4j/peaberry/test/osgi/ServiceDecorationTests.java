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
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * Test service decoration.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(testName = "ServiceDecorationTests", suiteName = "OSGi")
public final class ServiceDecorationTests
    extends OSGiServiceTester {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    binder.bind(iterable(SimpleService.class)).annotatedWith(named("Broken")).toProvider(
        service(SimpleService.class).direct().decoratedWith(Key.get(BrokenDecorator.class))
            .multiple());

    binder.bind(SimpleService.class).annotatedWith(named("Sticky")).toProvider(
        service(SimpleService.class).decoratedWith(Key.get(StickyDecorator.class)).single());

    binder.bind(Holder.class);
  }

  static class BrokenDecorator
      implements ImportDecorator<Object> {
    @SuppressWarnings("unused")
    public <T> Import<T> decorate(Import<T> handle) {
      return new Import<T>() {
        public T get() {
          throw new UnsupportedOperationException();
        }

        public void unget() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }

  static class StickyDecorator
      implements ImportDecorator<Object> {

    public <T> Import<T> decorate(final Import<T> handle) {

      return new Import<T>() {
        private volatile T instance;

        public T get() {
          if (null == instance) {
            synchronized (this) {
              if (null == instance) {
                instance = handle.get();
              }
            }
          }
          if (null == instance) {
            throw new ServiceUnavailableException();
          }
          return instance;
        }

        public void unget() {}
      };
    }
  }

  static class Holder {
    @Inject
    @Named("Broken")
    Iterable<SimpleService> brokenServices;
  }

  @Inject
  Injector injector;

  @Inject
  @Named("Sticky")
  SimpleService stickyService;

  public void brokenServices() {
    disableAllServices();

    enableService("A");
    enableService("B");
    enableService("C");

    Holder holder = injector.getInstance(Holder.class);
    checkServices(holder.brokenServices);

    disableAllServices();
  }

  public void stickyService() {
    disableAllServices();
    missingService(stickyService);
    enableService("A");
    checkService(stickyService, "A");
    enableService("B");
    checkService(stickyService, "A");
    disableService("A");
    missingService(stickyService);
    disableAllServices();
  }
}
