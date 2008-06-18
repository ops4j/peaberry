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
import static org.ops4j.peaberry.Peaberry.osgiServiceRegistry;
import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;

import java.util.Iterator;
import java.util.Map;

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher.Handle;
import org.ops4j.peaberry.test.osgi.OSGiServiceTester.SimpleService;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Test service scoping.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ServiceScopingTests", suiteName = "OSGi")
public class ServiceScopingTests {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    final Key<ServiceRegistry> registryKey = Key.get(ServiceRegistry.class, named("counting"));
    final Key<SimpleService> serviceKey = Key.get(SimpleService.class, named("counting"));

    binder.bind(registryKey).toProvider(CountingRegistryProvider.class);
    binder.bind(serviceKey).toProvider(CountingServiceProvider.class);

    binder.bind(SimpleService.class).toProvider(
        service(SimpleService.class).registry(registryKey).single());

    binder.bind(Handle.class).toProvider(registration(serviceKey).registry(registryKey).handle());
  }

  protected static class CountingRegistryProvider
      implements Provider<ServiceRegistry> {

    @Inject
    BundleContext bundleContext;

    public static volatile int lookupCount = 0;
    public static volatile int watchCount = 0;

    public ServiceRegistry get() {
      final ServiceRegistry osgiRegistry = osgiServiceRegistry(bundleContext);
      return new ServiceRegistry() {
        public <T> Iterator<T> lookup(final Class<? extends T> clazz, final String filter) {
          lookupCount++;
          return osgiRegistry.lookup(clazz, filter);
        }

        public <T, S extends T> Handle<T> add(final S service, final Map<String, ?> attributes) {
          watchCount++;
          return osgiRegistry.add((T) service, attributes);
        }
      };
    }
  }

  protected static class CountingServiceProvider
      implements Provider<SimpleService> {

    public SimpleService get() {
      return new SimpleService() {
        public String check() {

          final String lookupCount = "lookup:" + CountingRegistryProvider.lookupCount;
          final String watchCount = "watch:" + CountingRegistryProvider.watchCount;

          return lookupCount + ',' + watchCount;
        }
      };
    }
  }

  @Inject
  SimpleService service;

  @Inject
  Injector injector;

  public void checkScoping() {

    @SuppressWarnings("unchecked")
    Handle handle = injector.getInstance(Handle.class);

    assert service.check().equals("lookup:1,watch:1");
    assert service.check().equals("lookup:2,watch:1");
    assert service.check().equals("lookup:3,watch:1");

    handle.remove();

    handle = injector.getInstance(Handle.class);

    assert service.check().equals("lookup:4,watch:2");
    assert service.check().equals("lookup:5,watch:2");
    assert service.check().equals("lookup:6,watch:2");

    handle.remove();
  }
}
