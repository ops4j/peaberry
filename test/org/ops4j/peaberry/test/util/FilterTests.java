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

import java.util.Hashtable;
import java.util.Map;

import org.ops4j.peaberry.util.Attributes;
import org.ops4j.peaberry.util.Filters;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
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
    final String filter = Filters.objectClass(A.class, B.class, C.class);
    final Map<String, ?> attributes = Attributes.objectClass(A.class, B.class, C.class);

    try {
      assert FrameworkUtil.createFilter(filter).match(new Hashtable<String, Object>(attributes));
    } catch (final InvalidSyntaxException e) {
      assert false : "Unexpected exception " + e;
    }
  }
}
