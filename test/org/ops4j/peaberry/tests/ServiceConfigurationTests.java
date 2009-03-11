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

package org.ops4j.peaberry.tests;

import static org.ops4j.peaberry.Peaberry.service;
import static org.testng.Assert.fail;

import java.util.Map;

import org.testng.annotations.Test;

import com.google.inject.Key;

/**
 * Test configuration by using the service builder.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceConfigurationTests {

  @SuppressWarnings("unchecked")
  public void testBrokenConfiguration() {
    try {
      service((Object) null).attributes((Key<Map<String, ?>>) null);
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}

    try {
      service((Object) null).attributes((Map<String, ?>) null);
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}

    try {
      service((Object) null).attributes((Key) Key.get(Map.class)).export().get();
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}
  }
}
