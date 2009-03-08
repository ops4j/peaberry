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

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.testng.annotations.Test;

/**
 * Test sticky service injection.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class StickyServiceTests {

  public void testStickiness() {
    final String[] placeholder = new String[1];

    final Import<String> target = new Import<String>() {

      public String get() {
        return placeholder[0];
      }

      @SuppressWarnings("unchecked")
      public Map<String, ?> attributes() {
        return null == placeholder[0] ? null : Collections.EMPTY_MAP;
      }

      public void unget() {}
    };

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
        return null;
      }
    }).decorate(target);

    assertNull(staticService.get());
    assertNull(reusableService.get());
    assertNull(brokenService.get());

    placeholder[0] = "one";

    assertEquals(staticService.get(), "one");
    assertEquals(reusableService.get(), "one");
    assertEquals(brokenService.get(), "one");

    placeholder[0] = "two";

    assertEquals(staticService.get(), "one");
    assertEquals(reusableService.get(), "one");
    assertEquals(brokenService.get(), "one");

    placeholder[0] = null;

    assertNull(staticService.get());
    assertNull(reusableService.get());
    try {
      assertNull(brokenService.get());
      fail("Expected ServiceException");
    } catch (final ServiceException e) {}

    placeholder[0] = "three";

    assertNull(staticService.get());
    assertNull(staticService.attributes());
    assertEquals(reusableService.get(), "three");
    assertNull(brokenService.get());
  }
}
