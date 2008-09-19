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

import static org.ops4j.peaberry.Peaberry.service;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import examples.types.TypeService;

/**
 * Run various type signature tests to exercise service proxy creation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceTypeTests
    extends InjectableTestCase {

  @Inject
  TypeService typeService;

  @Override
  protected void configure() {
    bind(TypeService.class).toProvider(service(TypeService.class).single());
  }

  public void testServiceTypesAndSignatures()
      throws Exception {

    typeService.parse(typeService.toString());

    assertEquals(typeService.parseBoolean(typeService.toString(true)), true);
    assertEquals(typeService.parseByte(typeService.toString((byte) 1)), 1);
    assertEquals(typeService.parseChar(typeService.toString((char) 2)), 2);
    assertEquals(typeService.parseShort(typeService.toString((short) 3)), 3);
    assertEquals(typeService.parseInt(typeService.toString(4)), 4);
    assertEquals(typeService.parseLong(typeService.toString((long) 5)), 5l);
    assertEquals(typeService.parseFloat(typeService.toString((float) 6.7)), 6.7f);
    assertEquals(typeService.parseDouble(typeService.toString(8.9)), 8.9d);
    assertEquals(typeService.parseObject(typeService.toString("10")), "10");

    assertTrue(Arrays.equals(typeService.parseBooleanArray(typeService.toString(new boolean[] {
        true, false})), new boolean[] {true, false}));
    assertTrue(Arrays.equals(typeService.parseByteArray(typeService.toString(new byte[] {1, 2})),
        new byte[] {1, 2}));
    assertTrue(Arrays.equals(typeService
        .parseCharArray(typeService.toString(new char[] {'3', '4'})), new char[] {'3', '4'}));
    assertTrue(Arrays.equals(typeService.parseShortArray(typeService.toString(new short[] {5, 6})),
        new short[] {5, 6}));
    assertTrue(Arrays.equals(typeService.parseIntArray(typeService.toString(new int[] {7, 8})),
        new int[] {7, 8}));
    assertTrue(Arrays.equals(
        typeService.parseLongArray(typeService.toString(new long[] {9l, 10l})),
        new long[] {9l, 10l}));
    assertTrue(Arrays.equals(typeService.parseFloatArray(typeService.toString(new float[] {11.12f,
        13.14f})), new float[] {11.12f, 13.14f}));
    assertTrue(Arrays.equals(typeService.parseDoubleArray(typeService.toString(new double[] {
        15.16d, 17.18d})), new double[] {15.16d, 17.18d}));
    assertTrue(Arrays.equals(typeService.parseObjectArray(typeService.toString(new Object[] {"19",
        "20"})), new Object[] {"19", "20"}));

    assertTrue(Arrays.equals(
        typeService.parseIntArray(typeService.toString(int.class, 24, 25, 26)), new int[] {24, 25,
            26}));
    assertTrue(Arrays.equals(typeService.parseObjectArray(typeService.toString(String.class, "27",
        "28", "29")), new String[] {"27", "28", "29"}));

    assertEquals(typeService.parseGenericType(Integer.class, typeService.toString(Arrays.asList(21,
        22, 23))), Arrays.asList(21, 22, 23));
  }
}
