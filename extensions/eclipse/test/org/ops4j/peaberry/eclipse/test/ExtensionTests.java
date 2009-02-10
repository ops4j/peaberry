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

package org.ops4j.peaberry.eclipse.test;

import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.eclipse.EclipseRegistry.eclipseRegistry;
import static org.ops4j.peaberry.test.Director.findContext;

import java.util.Collections;
import java.util.Map;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.eclipse.ExtensionInterface;
import org.ops4j.peaberry.eclipse.GuiceExtensionFactory;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import examples.menu.Item;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ExtensionTests {

  @ExtensionInterface("examples.menu.items")
  private static interface ItemFacade {
    String getName();
  }

  @Inject
  final Injector injector;

  @Inject
  ServiceRegistry registry;

  public ExtensionTests() {
    final BundleContext context = findContext(getClass());
    injector = Guice.createInjector(osgiModule(context, eclipseRegistry()));
    GuiceExtensionFactory.publishInjector(context, injector, context.getBundles());
    injector.injectMembers(this);
  }

  public void testExtensionPoint() {
    registry.add(new Import<Item>() {

      public Item get() {
        return new Item() {
          public String getName() {
            return "Exit the application";
          }
        };
      }

      public Map<String, ?> attributes() {
        return Collections.emptyMap();
      }

      public void unget() {}
    });

    System.out.println("@ExtensionInterface proxy");
    System.out.println("----------------------------------------------------------------");
    for (final Import<ItemFacade> item : registry.lookup(ItemFacade.class, null)) {
      System.out.println("getClass(): " + item.get().getClass());
      System.out.println("toString(): " + item.get().toString());
      System.out.println("getName():  " + item.get().getName());
      System.out.println("equals():   " + item.get().equals(item.get()));
      System.out.println("----------------------------------------------------------------");
    }
    System.out.println("\ncreateExecutableExtension");
    System.out.println("----------------------------------------------------------------");
    for (final Import<Item> item : registry.lookup(Item.class, null)) {
      System.out.println("getClass(): " + item.get().getClass());
      System.out.println("toString(): " + item.get().toString());
      System.out.println("getName():  " + item.get().getName());
      System.out.println("equals():   " + item.get().equals(item.get()));
      System.out.println("----------------------------------------------------------------");
    }
    System.out.println();
  }
}
