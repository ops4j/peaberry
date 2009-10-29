/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.eclipse.tests;

import static com.google.inject.Guice.createInjector;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.eclipse.EclipseRegistry.eclipseRegistry;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.eclipse.ExtensionBean;
import org.ops4j.peaberry.eclipse.GuiceExtensionFactory;
import org.ops4j.peaberry.util.StaticImport;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import examples.menu.Item;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ExtensionTests {

  private static BundleContext BUNDLE_CONTEXT;

  @Test(enabled = false)
  public static void setBundle(final Bundle bundle) {
    BUNDLE_CONTEXT = bundle.getBundleContext();
  }

  @Target({METHOD, TYPE})
  @Retention(RUNTIME)
  public @interface MapName {
    String value();
  }

  @Target(METHOD)
  @Retention(RUNTIME)
  public @interface MapContent {}

  @ExtensionBean("examples.menu.items")
  private static interface ItemFacade {
    String getLabel();

    @MapName("label")
    String getSomeTag();

    @org.ops4j.peaberry.eclipse.MapName("label")
    String getAnotherTag();

    @MapContent
    String getContent();
  }

  @Inject
  ServiceRegistry registry;

  public ExtensionTests() {
    createInjector(osgiModule(BUNDLE_CONTEXT, eclipseRegistry())).injectMembers(this);
  }

  public void testExtensionPoint() {
    registry.add(new StaticImport<Item>(new Item() {
      public String getLabel() {
        return "Service-based menu item";
      }
    }));

    System.out.println("@ExtensionBean proxy");
    System.out.println("----------------------------------------------------------------");
    for (final Import<ItemFacade> item : registry.lookup(ItemFacade.class, null)) {
      System.out.println("getClass(): " + item.get().getClass());
      System.out.println("toString(): " + item.get().toString());
      System.out.println("getLabel(): " + item.get().getLabel());
      System.out.println("equals():   " + item.get().equals(item.get()));
      System.out.println("----------------------------------------------------------------");

      // confirm @MapName/@MapContent works in the generated bean
      assertEquals(item.get().getSomeTag(), item.get().getLabel());
      assertEquals(item.get().getAnotherTag(), item.get().getLabel());
      assertNull(item.get().getContent()); // no content in example
    }
    System.out.println("\ncreateExecutableExtension");
    System.out.println("----------------------------------------------------------------");
    for (final Import<Item> item : registry.lookup(Item.class, null)) {
      System.out.println("getClass(): " + item.get().getClass());
      System.out.println("toString(): " + item.get().toString());
      System.out.println("getLabel(): " + item.get().getLabel());
      System.out.println("equals():   " + item.get().equals(item.get()));
      System.out.println("----------------------------------------------------------------");
    }
    System.out.println();

    GuiceExtensionFactory.cleanup();
  }
}
