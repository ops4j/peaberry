/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"){}
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

package examples.types.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import examples.types.TypeMapper;

/**
 * {@link TypeMapper} implementation, uses standard type-to-string conversions.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class TypeMapperImpl
    implements TypeMapper {

  @Override
  public String toString() {
    return "void";
  }

  public String toString(final boolean b) {
    return Boolean.toString(b);
  }

  public String toString(final byte b) {
    return Byte.toString(b);
  }

  public String toString(final char c) {
    return Character.toString(c);
  }

  public String toString(final short s) {
    return Short.toString(s);
  }

  public String toString(final int i) {
    return Integer.toString(i);
  }

  public String toString(final long l) {
    return Long.toString(l);
  }

  public String toString(final float f) {
    return Float.toString(f);
  }

  public String toString(final double d) {
    return Double.toString(d);
  }

  public String toString(final Object o) {
    return o.toString();
  }

  public String toString(final boolean[] bs) {
    return Arrays.toString(bs);
  }

  public String toString(final byte[] bs) {
    return Arrays.toString(bs);
  }

  public String toString(final char[] cs) {
    return Arrays.toString(cs);
  }

  public String toString(final short[] ss) {
    return Arrays.toString(ss);
  }

  public String toString(final int[] is) {
    return Arrays.toString(is);
  }

  public String toString(final long[] ls) {
    return Arrays.toString(ls);
  }

  public String toString(final float[] fs) {
    return Arrays.toString(fs);
  }

  public String toString(final double[] ds) {
    return Arrays.toString(ds);
  }

  public String toString(final Object[] os) {
    return Arrays.toString(os);
  }

  public <T> String toString(final List<T> ts) {
    return ts.toString();
  }

  public String toString(final Class<?> c, final int... is) {
    return Arrays.toString(is);
  }

  public String toString(final Class<?> c, final Object... os) {
    return Arrays.toString(os);
  }

  public void parse(final String s) {}

  public boolean parseBoolean(final String s) {
    return Boolean.parseBoolean(s);
  }

  public byte parseByte(final String s) {
    return Byte.parseByte(s);
  }

  public char parseChar(final String s) {
    return s.charAt(0);
  }

  public short parseShort(final String s) {
    return Short.parseShort(s);
  }

  public int parseInt(final String s) {
    return Integer.parseInt(s);
  }

  public long parseLong(final String s) {
    return Long.parseLong(s);
  }

  public float parseFloat(final String s) {
    return Float.parseFloat(s);
  }

  public double parseDouble(final String s) {
    return Double.parseDouble(s);
  }

  public Object parseObject(final String s) {
    return s;
  }

  private String[] parseArray(final String s) {
    return s.substring(1, s.length() - 1).split(",");
  }

  public boolean[] parseBooleanArray(final String s) {
    final String[] elems = parseArray(s);
    final boolean[] bs = new boolean[elems.length];
    for (int i = 0; i < bs.length; i++) {
      bs[i] = Boolean.parseBoolean(elems[i].trim());
    }
    return bs;
  }

  public byte[] parseByteArray(final String s) {
    final String[] elems = parseArray(s);
    final byte[] bs = new byte[elems.length];
    for (int i = 0; i < bs.length; i++) {
      bs[i] = Byte.parseByte(elems[i].trim());
    }
    return bs;
  }

  public char[] parseCharArray(final String s) {
    final String[] elems = parseArray(s);
    final char[] cs = new char[elems.length];
    for (int i = 0; i < cs.length; i++) {
      cs[i] = elems[i].trim().charAt(0);
    }
    return cs;
  }

  public short[] parseShortArray(final String s) {
    final String[] elems = parseArray(s);
    final short[] ss = new short[elems.length];
    for (int i = 0; i < ss.length; i++) {
      ss[i] = Short.parseShort(elems[i].trim());
    }
    return ss;
  }

  public int[] parseIntArray(final String s) {
    final String[] elems = parseArray(s);
    final int[] is = new int[elems.length];
    for (int i = 0; i < is.length; i++) {
      is[i] = Integer.parseInt(elems[i].trim());
    }
    return is;
  }

  public long[] parseLongArray(final String s) {
    final String[] elems = parseArray(s);
    final long[] ls = new long[elems.length];
    for (int i = 0; i < ls.length; i++) {
      ls[i] = Long.parseLong(elems[i].trim());
    }
    return ls;
  }

  public float[] parseFloatArray(final String s) {
    final String[] elems = parseArray(s);
    final float[] fs = new float[elems.length];
    for (int i = 0; i < fs.length; i++) {
      fs[i] = Float.parseFloat(elems[i].trim());
    }
    return fs;
  }

  public double[] parseDoubleArray(final String s) {
    final String[] elems = parseArray(s);
    final double[] ds = new double[elems.length];
    for (int i = 0; i < ds.length; i++) {
      ds[i] = Double.parseDouble(elems[i].trim());
    }
    return ds;
  }

  public Object[] parseObjectArray(final String s) {
    final String[] elems = parseArray(s);
    final Object[] os = new Object[elems.length];
    for (int i = 0; i < os.length; i++) {
      os[i] = elems[i].trim();
    }
    return os;
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> parseGenericType(final Class<T> clazz, final String s)
      throws SecurityException, NoSuchMethodException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    final Constructor<T> ctor = clazz.getConstructor(String.class);
    final String[] elems = parseArray(s);
    final T[] os = (T[]) Array.newInstance(clazz, elems.length);
    for (int i = 0; i < os.length; i++) {
      os[i] = ctor.newInstance(elems[i].trim());
    }
    return Arrays.asList(os);
  }
}
