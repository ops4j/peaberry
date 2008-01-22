/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry.test;

import junit.framework.TestCase;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.osgi.OSGiService;
import com.google.inject.osgi.Peaberry;
import com.google.inject.util.GuiceContainer;

public class OSGiServiceBindingTest extends TestCase {

  interface Foo {
  }

  Foo m_logService;

  public OSGiServiceBindingTest() {
  }

  @Inject
  public OSGiServiceBindingTest(@OSGiService
  Foo foo) {
  }

  void setFoo(Foo foo) {
  }

  class Module extends AbstractModule {
    @Override
    protected void configure() {
      bind(OSGiServiceBindingTest.class).in(Scopes.SINGLETON);
    }
  }

  public void testInjection() {
    Injector injector =
      GuiceContainer.createInjector(Peaberry.getClassLoaderHook(), Peaberry
        .getModule(OSGiTests.getBundleContext()), new Module());

    injector.getInstance(OSGiServiceBindingTest.class);
  }
}
