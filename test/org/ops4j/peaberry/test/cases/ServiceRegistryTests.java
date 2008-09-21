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

package org.ops4j.peaberry.test.cases;

import static com.google.inject.name.Names.named;
import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.builders.DynamicServiceBuilder;
import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;

/**
 * Test single and multiple service injection, plus iterator flexibility.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceRegistryTests {

  @Singleton
  @SuppressWarnings("unchecked")
  static class MockServiceRegistry
      implements ServiceRegistry {

    final Map<Object, Map> registry = new HashMap<Object, Map>();

    public <T> Iterable<Import<T>> lookup(final Class<? extends T> clazz,
        final AttributeFilter filter) {
      return new Iterable() {
        public Iterator iterator() {

          final List<Import<T>> imports = new ArrayList<Import<T>>();

          for (final Entry<Object, Map> e : registry.entrySet()) {
            final Object service = e.getKey();

            if (clazz.isInstance(service) && (null == filter || filter.matches(e.getValue()))) {
              imports.add(new Import<T>() {

                public T get() {
                  return (T) service;
                }

                public void unget() {}
              });
            }
          }

          return imports.iterator();
        }
      };
    }

    public <S, T extends S> Export<S> export(final T service, final Map<String, ?> attributes) {
      registry.put(service, attributes);

      return new Export() {

        public void modify(final Map newAttributes) {
          registry.put(service, newAttributes);
        }

        public void remove() {
          registry.remove(service);
        }

        public Object get() {
          return service;
        }

        public void unget() {}
      };
    }
  }

  @Inject
  ClassLoader loader;

  @Inject
  Iterable<ClassLoader> loaders;

  public void testCustomServiceRegistry() {
    final Injector injector = Guice.createInjector(new AbstractModule() {

      @Override
      protected void configure() {
        final Key<? extends ServiceRegistry> registryKey = Key.get(MockServiceRegistry.class);
        final Key<ClassLoader> loaderImplKey = Key.get(ClassLoader.class, named("impl"));

        final DynamicServiceBuilder<ClassLoader> builder =
            service(ClassLoader.class).in(registryKey);

        bind(ClassLoader.class).toProvider(builder.single());
        bind(iterable(ClassLoader.class)).toProvider(builder.multiple());

        bind(export(ClassLoader.class)).toProvider(
            registration(loaderImplKey).in(registryKey).export());

        bind(loaderImplKey).toInstance(getClass().getClassLoader());
      }
    });

    injector.injectMembers(this);

    final Export<? extends ClassLoader> exportedLoader =
        injector.getInstance(Key.get(export(ClassLoader.class)));

    try {
      System.out.println(loader.loadClass(getClass().getName()));
    } catch (final ClassNotFoundException e) {
      e.printStackTrace();
    }

    exportedLoader.remove();
  }
}
