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

package org.ops4j.peaberry.test.cases;

import static org.ops4j.peaberry.util.Attributes.names;
import static org.ops4j.peaberry.util.Attributes.properties;
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
    final Properties sysProp = new Properties();

    sysProp.putAll(System.getProperties());
    sysProp.setProperty("string", "one");
    sysProp.put("integer", 1);

    final Map<String, ?> cleanAttributes = properties(sysProp);

    sysProp.put(1, "badKey");

    final Map<String, ?> fixedAttributes = properties(sysProp);

    assertNull(fixedAttributes.get(1));

    assertEquals(fixedAttributes.get("string"), "one");
    assertEquals(fixedAttributes.get("integer"), 1);

    assertEquals(fixedAttributes.size(), System.getProperties().size() + 2);

    assertEquals(cleanAttributes, fixedAttributes);
  }

  public void testNameAttributes() {
    try {
      names("cn=John Doe", "=", "dc=com");
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}
  }
}
