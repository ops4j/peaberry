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

package org.ops4j.peaberry.test.internal;

import static org.ops4j.peaberry.util.Attributes.attributes;

import java.util.Map;
import java.util.Properties;

import org.testng.annotations.Test;

/**
 * Test attribute helper methods.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "AttributeTests", suiteName = "Internal")
public final class AttributeTests {

  public void testPropertyConverter() {
    final Properties properties = new Properties();

    properties.put(1, "badKey");
    properties.setProperty("string", "one");
    properties.put("integer", 1);

    final Map<String, ?> attributes = attributes(properties);

    assert null == attributes.get(1);
    assert attributes.get("string").equals("one");
    assert attributes.get("integer").equals(1);

    assert 2 == attributes.size();
  }

  public void testAnnotationConverter() {
    final Map<String, ?> attributes = attributes("a=b", "=", "c=d");

    assert attributes.get("a").equals("b");
    assert attributes.get("c").equals("d");

    assert 2 == attributes.size();
  }
}
