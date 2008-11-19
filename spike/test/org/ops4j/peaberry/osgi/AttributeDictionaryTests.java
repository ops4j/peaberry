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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import org.testng.annotations.Test;

/**
 * Check that the attribute-dictionary adapter behaves as expected.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class AttributeDictionaryTests {

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
