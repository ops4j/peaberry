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

package org.ops4j.peaberry.tests;

import static org.ops4j.peaberry.util.Decorators.sticky;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

import java.util.concurrent.Callable;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.util.SimpleExport;
import org.ops4j.peaberry.util.StaticImport;
import org.testng.annotations.Test;

/**
 * Test sticky service injection.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class StickyServiceTests {

  public void testStickiness() {
    final Export<String> target = new SimpleExport<String>(new StaticImport<String>(null));

    final Import<String> reusableService = sticky(new Callable<Boolean>() {
      public Boolean call() {
        return true;
      }
    }).decorate(target);

    final Import<String> staticService = sticky(new Callable<Boolean>() {
      public Boolean call() {
        return false;
      }
    }).decorate(target);

    final Import<String> brokenService = sticky(new Callable<Boolean>() {
      public Boolean call() {
        return null; // invalid response
      }
    }).decorate(target);

    assertNull(staticService.get());
    assertNull(reusableService.get());
    assertNull(brokenService.get());

    target.put("one");

    assertEquals(staticService.get(), "one");
    assertEquals(reusableService.get(), "one");
    assertEquals(brokenService.get(), "one");

    target.put("two");

    assertEquals(staticService.get(), "one");
    assertEquals(reusableService.get(), "one");
    assertEquals(brokenService.get(), "one");

    target.put(null);

    assertNull(staticService.get());
    assertNull(reusableService.get());
    try {
      assertNull(brokenService.get());
      fail("Expected ServiceException");
    } catch (final ServiceException e) {}

    target.put("three");

    assertNull(staticService.get());
    assertEquals(reusableService.get(), "three");
    assertNull(brokenService.get());

    target.unput();

    assertNull(staticService.get());
    assertNull(reusableService.get());
    assertNull(brokenService.get());
  }
}
