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

package org.ops4j.peaberry.internal;

import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.singletonList;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.util.StaticImport;
import org.testng.annotations.Test;

/**
 * Corner-case tests for concurrent imports.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ConcurrentImportTests {

  public void testConcurrentProperties() {

    @SuppressWarnings("unchecked")
    final Import<?> noImports = new ConcurrentImport<Object>(EMPTY_LIST);

    final Import<?> nullImport =
        new ConcurrentImport<Object>(singletonList((Import<Object>) new StaticImport<Object>(null)));

    assertNull(noImports.attributes());
    assertNull(nullImport.attributes());

    assertFalse(noImports.available());
    assertTrue(nullImport.available()); // we don't yet know it's null

    noImports.get();
    nullImport.get();

    assertNull(noImports.attributes());
    assertNull(nullImport.attributes());

    assertFalse(noImports.available());
    assertFalse(nullImport.available()); // now we know it's null
  }
}
