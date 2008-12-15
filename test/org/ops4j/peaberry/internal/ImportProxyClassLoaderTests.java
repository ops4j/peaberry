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

package org.ops4j.peaberry.internal;

import static org.ops4j.peaberry.internal.ImportProxyClassLoader.getProxyConstructor;
import static org.testng.Assert.fail;

import java.lang.reflect.Constructor;

import org.ops4j.peaberry.ServiceException;
import org.testng.annotations.Test;

/**
 * Corner-case tests for custom proxy classloader.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ImportProxyClassLoaderTests {

  public void testGetProxyConstructor() {

    try {
      // cannot proxy a final class
      getProxyConstructor(String.class);
      fail("Expected ServiceException");
    } catch (final ServiceException e) {}

    // can proxy interface from java.*
    getProxyConstructor(Runnable.class);

    try {
      // check proxy classloader hides its own internal classes
      final Constructor<?> ctor = getProxyConstructor(Object.class);
      ctor.getDeclaringClass().getClassLoader().loadClass(ImportGlue.class.getName());
      fail("Expected ClassNotFoundException");
    } catch (final ClassNotFoundException e) {}
  }
}
