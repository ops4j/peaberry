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

package examples.types;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Exercise service proxy creation by supplying various type signatures.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface TypeService {

  String toString();

  String toString(boolean b);

  String toString(byte b);

  String toString(char c);

  String toString(short s);

  String toString(int i);

  String toString(long l);

  String toString(float f);

  String toString(double d);

  String toString(Object o);

  String toString(boolean[] bs);

  String toString(byte[] bs);

  String toString(char[] cs);

  String toString(short[] ss);

  String toString(int[] is);

  String toString(long[] ls);

  String toString(float[] fs);

  String toString(double[] ds);

  String toString(Object[] os);

  <T> String toString(List<T> ts);

  @SuppressWarnings("unchecked")
  String toString(Class c, int... js);

  @SuppressWarnings("unchecked")
  String toString(Class c, Object... os);

  void parse(String s);

  boolean parseBoolean(String s);

  byte parseByte(String s);

  char parseChar(String s);

  short parseShort(String s);

  int parseInt(String s);

  long parseLong(String s);

  float parseFloat(String s);

  double parseDouble(String s);

  Object parseObject(String s);

  boolean[] parseBooleanArray(String s);

  byte[] parseByteArray(String s);

  char[] parseCharArray(String s);

  short[] parseShortArray(String s);

  int[] parseIntArray(String s);

  long[] parseLongArray(String s);

  float[] parseFloatArray(String s);

  double[] parseDoubleArray(String s);

  Object[] parseObjectArray(String s);

  <T> List<T> parseGenericType(Class<T> clazz, String s)
      throws SecurityException, NoSuchMethodException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException;
}
