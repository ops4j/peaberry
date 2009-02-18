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

import static java.util.Collections.singletonMap;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.util.Attributes;
import org.ops4j.peaberry.util.Filters;
import org.testng.annotations.Test;

/**
 * Test service filter behaviour.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceFilterTests {

  interface A {}

  interface B {}

  interface C {}

  public void testSingleObjectClassFilter() {
    final AttributeFilter filter = Filters.objectClass(A.class);
    final Map<String, ?> attributes = Attributes.objectClass(A.class);

    assertTrue(filter.matches(attributes));
  }

  public void testMultipleObjectClassFilter() {
    final AttributeFilter filter = Filters.objectClass(B.class, C.class, A.class);
    final Map<String, ?> attributes = Attributes.objectClass(A.class, B.class, C.class);

    assertTrue(filter.matches(attributes));
  }

  public void testFilterHashCodeAndEquals() {
    final AttributeFilter filterA = Filters.ldap("(language=french)");
    final AttributeFilter filterB = Filters.ldap("  (  language  =french)");

    assertEquals(filterA, filterB);
    assertEquals(filterB, filterA);
    assertFalse(filterA.equals(null));

    assertEquals(filterA.hashCode(), filterB.hashCode());
  }

  public void testBrokenLdapFilterStrings() {
    try {
      Filters.ldap("missing-brackets");
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}

    try {
      Filters.ldap("(food=pizza)leftovers");
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}

    try {
      Filters.ldap("(name=*bad-type*)").matches(singletonMap("name", true));
      fail("Expected ServiceException");
    } catch (final ServiceException e) {}
  }
}
