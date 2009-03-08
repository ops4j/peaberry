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

package org.ops4j.peaberry.tests;

import static com.google.inject.name.Names.named;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceRegistry;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * Test using a custom service registry (non-OSGi).
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceRegistryTests
    extends InjectableTestCase {

  @Inject
  Export<ClassLoader> exportedLoader;

  @Inject
  @Named("service")
  ClassLoader importedLoader;

  public ServiceRegistryTests() {
    super(new TrivialServiceRegistry());
  }

  @Override
  protected void configure() {
    final Key<? extends ServiceRegistry> alternateRegistryKey =
        Key.get(ServiceRegistry.class, named("alternate"));

    bind(ClassLoader.class).annotatedWith(named("service")).toProvider(
        service(ClassLoader.class).in(alternateRegistryKey).single());

    bind(export(ClassLoader.class)).toProvider(
        service(getClass().getClassLoader()).in(alternateRegistryKey).export());
  }

  public void testCustomRegistry() {

    try {
      assertEquals(importedLoader.loadClass(getClass().getName()), getClass());
    } catch (final ClassNotFoundException e) {
      fail("Unexpected ClassNotFoundException", e);
    }

    assertEquals(importedLoader.hashCode(), getClass().getClassLoader().hashCode());

    exportedLoader.unput();
  }
}
