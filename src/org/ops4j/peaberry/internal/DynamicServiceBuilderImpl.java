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

package org.ops4j.peaberry.internal;

import java.util.Iterator;

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.DynamicServiceBuilder;
import org.ops4j.peaberry.builders.FilteredServiceBuilder;
import org.ops4j.peaberry.builders.ScopedServiceBuilder;
import org.ops4j.peaberry.builders.ServiceProxyBuilder;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.internal.GuiceCodeGen;
import com.google.inject.internal.cglib.proxy.Dispatcher;
import com.google.inject.internal.cglib.proxy.Enhancer;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class DynamicServiceBuilderImpl<T>
    implements DynamicServiceBuilder<T> {

  final Class<? extends T> clazz;

  int leaseInSeconds = 0;
  String filter = null;

  Key<? extends ServiceRegistry> registryKey = Key.get(ServiceRegistry.class);

  public DynamicServiceBuilderImpl(final Class<? extends T> clazz) {
    this.clazz = clazz;
  }

  public FilteredServiceBuilder<T> leased(final int seconds) {
    leaseInSeconds = seconds;
    return this;
  }

  public FilteredServiceBuilder<T> constant() {
    leaseInSeconds = -1;
    return this;
  }

  public ScopedServiceBuilder<T> filter(final String customFilter) {
    filter = customFilter;
    return this;
  }

  public ServiceProxyBuilder<T> registry(final Key<? extends ServiceRegistry> key) {
    registryKey = key;
    return this;
  }

  ServiceRegistry getServiceRegistry(final Injector injector) {
    final ServiceRegistry registry = injector.getInstance(registryKey);
    if (leaseInSeconds != 0) {
      return new LeasedServiceRegistry(registry, leaseInSeconds);
    }
    return registry;
  }

  public Provider<T> single() {
    return new Provider<T>() {

      @Inject
      Injector injector;

      public T get() {
        final ServiceRegistry registry = getServiceRegistry(injector);

        final Enhancer proxy = GuiceCodeGen.newEnhancer(clazz);
        proxy.setCallback(new Dispatcher() {
          public Object loadObject() {
            try {
              // use first matching service from the dynamic query
              return registry.lookup(clazz, filter).next();
            } catch (final Exception e) {
              throw new ServiceUnavailableException(e);
            }
          }
        });

        return clazz.cast(proxy.create());
      }
    };
  }

  public Provider<Iterable<T>> multiple() {
    return new Provider<Iterable<T>>() {

      @Inject
      Injector injector;

      public Iterable<T> get() {
        final ServiceRegistry registry = getServiceRegistry(injector);

        return new Iterable<T>() {
          public Iterator<T> iterator() {
            return registry.lookup(clazz, filter);
          }
        };
      }
    };
  }
}
