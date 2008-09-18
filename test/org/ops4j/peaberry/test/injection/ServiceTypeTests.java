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

package org.ops4j.peaberry.test.injection;

import static org.ops4j.peaberry.Peaberry.service;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import examples.types.TypeService;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public class ServiceTypeTests
    extends InjectableTestCase {

  @Inject
  TypeService t;

  @Override
  protected void configure() {
    bind(TypeService.class).toProvider(service(TypeService.class).single());
  }

  public void testServiceTypes()
      throws Exception {

    t.parse(t.toString());
    assertEquals(t.parseBoolean(t.toString(true)), true);
    assertEquals(t.parseByte(t.toString((byte) 1)), 1);
    assertEquals(t.parseChar(t.toString((char) 2)), 2);
    assertEquals(t.parseShort(t.toString((short) 3)), 3);
    assertEquals(t.parseInt(t.toString(4)), 4);
    assertEquals(t.parseLong(t.toString((long) 5)), 5l);
    assertEquals(t.parseFloat(t.toString((float) 6.7)), 6.7f);
    assertEquals(t.parseDouble(t.toString(8.9)), 8.9d);
    assertEquals(t.parseObject(t.toString("10")), "10");
    assertTrue(Arrays.equals(t.parseBooleanArray(t.toString(new boolean[] {true, false})),
        new boolean[] {true, false}));
    assertTrue(Arrays.equals(t.parseByteArray(t.toString(new byte[] {1, 2})), new byte[] {1, 2}));
    assertTrue(Arrays.equals(t.parseCharArray(t.toString(new char[] {'3', '4'})), new char[] {'3',
        '4'}));
    assertTrue(Arrays.equals(t.parseShortArray(t.toString(new short[] {5, 6})), new short[] {5, 6}));
    assertTrue(Arrays.equals(t.parseIntArray(t.toString(new int[] {7, 8})), new int[] {7, 8}));
    assertTrue(Arrays.equals(t.parseLongArray(t.toString(new long[] {9l, 10l})), new long[] {9l,
        10l}));
    assertTrue(Arrays.equals(t.parseFloatArray(t.toString(new float[] {11.12f, 13.14f})),
        new float[] {11.12f, 13.14f}));
    assertTrue(Arrays.equals(t.parseDoubleArray(t.toString(new double[] {15.16d, 17.18d})),
        new double[] {15.16d, 17.18d}));
    assertTrue(Arrays.equals(t.parseObjectArray(t.toString(new Object[] {"19", "20"})),
        new Object[] {"19", "20"}));
    assertEquals(t.parseGenericType(Integer.class, t.toString(Arrays.asList(21, 22, 23))), Arrays
        .asList(21, 22, 23));
    assertTrue(Arrays.equals(t.parseIntArray(t.toString(int.class, 24, 25, 26)), new int[] {24, 25,
        26}));
    assertTrue(Arrays.equals(t.parseObjectArray(t.toString(String.class, "27", "28", "29")),
        new String[] {"27", "28", "29"}));
  }
}
