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

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.eclipse.EclipseRegistry;
import org.ops4j.peaberry.eclipse.ExtensionInterface;
import org.testng.annotations.Test;

import examples.menu.Item;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ExtensionTests {

  @ExtensionInterface("examples.menu")
  private static interface ItemFacade {
    String getName();
  }

  public void testExtensionPoint() {
    System.out.println("@ExtensionInterface proxy");
    System.out.println("----------------------------------------------------------------");
    for (final Import<ItemFacade> item : new EclipseRegistry().lookup(ItemFacade.class, null)) {
      System.out.println("getClass(): " + item.get().getClass());
      System.out.println("toString(): " + item.get().toString());
      System.out.println("getName():  " + item.get().getName());
      System.out.println("equals():   " + item.get().equals(item.get()));
      System.out.println("----------------------------------------------------------------");
    }
    System.out.println("\ncreateExecutableExtension");
    System.out.println("----------------------------------------------------------------");
    for (final Import<Item> item : new EclipseRegistry().lookup(Item.class, null)) {
      System.out.println("getClass(): " + item.get().getClass());
      System.out.println("toString(): " + item.get().toString());
      System.out.println("getName():  " + item.get().getName());
      System.out.println("equals():   " + item.get().equals(item.get()));
      System.out.println("----------------------------------------------------------------");
    }
    System.out.println();
  }
}
