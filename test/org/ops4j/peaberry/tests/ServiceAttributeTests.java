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

import static org.ops4j.peaberry.util.Attributes.names;
import static org.ops4j.peaberry.util.Attributes.objectClass;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.Attributes.union;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

import java.util.Map;
import java.util.Properties;

import org.testng.annotations.Test;

/**
 * Test service attribute behaviour.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceAttributeTests {

  public void testPropertyAttributes() {
    final Properties sysProp = new Properties(System.getProperties());

    sysProp.setProperty("string", "one");
    sysProp.put("integer", 1);

    final Map<String, ?> cleanAttributes = properties(sysProp);

    sysProp.put(1, "badKey");

    try {
      properties(sysProp);
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}

    assertNull(cleanAttributes.get(1));

    assertEquals(cleanAttributes.get("string"), "one");
    assertEquals(cleanAttributes.get("integer"), 1);

    assertEquals(cleanAttributes.size(), System.getProperties().size() + 2);

    for (final String key : cleanAttributes.keySet()) {
      final Object value = cleanAttributes.get(key);
      assertEquals(value instanceof String ? value : null, sysProp.getProperty(key), key);
    }
  }

  public void testNameAttributes() {
    try {
      names("cn=John Doe", "=", "dc=com");
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}
  }

  interface A {}

  interface B {}

  public void testAttributeUnion() {
    final Map<String, ?> mapA = objectClass(A.class);
    final Map<String, ?> mapB = objectClass(B.class);

    assertEquals(union(mapA, null, mapB), mapB);
    assertEquals(union(null, mapB, mapA), mapA);
  }
}
