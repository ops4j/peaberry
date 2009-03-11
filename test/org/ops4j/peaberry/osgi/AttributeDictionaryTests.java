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

package org.ops4j.peaberry.osgi;

import static org.ops4j.peaberry.util.Attributes.properties;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import org.testng.annotations.Test;

/**
 * Check that the attribute-dictionary adapters behave as expected.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class AttributeDictionaryTests {

  public void testImmutableAttributes() {

    final ImmutableAttribute a1 = new ImmutableAttribute("a", "a");
    final ImmutableAttribute a2 = new ImmutableAttribute("a", "a");

    final ImmutableAttribute nullK = new ImmutableAttribute(null, "a");
    final ImmutableAttribute nullV = new ImmutableAttribute("a", null);

    assertEquals(a1, a2);

    assertEquals(nullK, nullK);
    assertEquals(nullV, nullV);

    assertEquals(a1.hashCode(), a2.hashCode());

    nullK.hashCode();
    nullV.hashCode();

    assertNotSame(a1, nullV);
    assertNotSame(nullK, nullV);
    assertNotSame(nullK, a1);

    assertFalse(a1.equals("test"));

    try {
      a2.setValue("c");
      fail("Expected UnsupportedOperationException");
    } catch (final UnsupportedOperationException e) {}
  }

  public void testAttributeDictionaryAdapter() {

    final Properties sysProp = System.getProperties();
    final Dictionary<String, Object> adapter = new AttributeDictionary(properties(sysProp));

    assertEquals(adapter.isEmpty(), sysProp.isEmpty());
    assertEquals(adapter.size(), sysProp.size());

    for (final Enumeration<Object> i = adapter.elements(); i.hasMoreElements();) {
      assertTrue(sysProp.containsValue(i.nextElement()));
    }

    try {
      adapter.put("this", "there");
      fail("Expected UnsupportedOperationException");
    } catch (final UnsupportedOperationException e) {}

    try {
      adapter.remove("this");
      fail("Expected UnsupportedOperationException");
    } catch (final UnsupportedOperationException e) {}
  }
}
