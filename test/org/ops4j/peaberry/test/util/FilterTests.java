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

package org.ops4j.peaberry.test.util;

import static java.util.Collections.singletonMap;

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.util.Attributes;
import org.ops4j.peaberry.util.Filters;
import org.testng.annotations.Test;

/**
 * Test filter helper methods.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(testName = "FilterTests", suiteName = "Util")
public final class FilterTests {

  interface A {}

  interface B {}

  interface C {}

  public void testObjectClassConverter() {
    final AttributeFilter filter = Filters.objectClass(B.class, C.class, A.class);
    final Map<String, ?> attributes = Attributes.objectClass(A.class, B.class, C.class);

    assert filter.matches(attributes);
  }

  public void testHashCodeAndEquals() {
    final AttributeFilter filterA = Filters.ldap("(language=french)");
    final AttributeFilter filterB = Filters.ldap("  (  language  =french)");

    assert filterA.equals(filterB);
    assert filterB.equals(filterA);
    assert !filterA.equals(null);

    assert filterA.hashCode() == filterB.hashCode();
  }

  public void testBogusFilterString() {
    try {
      Filters.ldap("missing-brackets");
      assert false : "Expected IllegalArgumentException";
    } catch (final IllegalArgumentException e) {}

    try {
      Filters.ldap("(food=pizza)leftovers");
      assert false : "Expected IllegalArgumentException";
    } catch (final IllegalArgumentException e) {}

    try {
      Filters.ldap("(name=*bad-type*)").matches(singletonMap("name", true));
      assert false : "Expected ServiceException";
    } catch (final ServiceException e) {}
  }
}
