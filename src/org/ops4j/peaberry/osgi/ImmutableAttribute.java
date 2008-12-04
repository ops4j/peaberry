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

import java.util.Map.Entry;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ImmutableAttribute
    implements Entry<String, Object> {

  private final String k;
  private final Object v;

  public ImmutableAttribute(final String key, final Object value) {
    k = key;
    v = value;
  }

  public String getKey() {
    return k;
  }

  public Object getValue() {
    return v;
  }

  public Object setValue(final Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(final Object rhs) {
    if (rhs instanceof Entry) {
      final Entry entry = (Entry) rhs;
      return equals(k, entry.getKey()) && equals(v, entry.getValue());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (null == k ? 0 : k.hashCode()) ^ (null == v ? 0 : v.hashCode());
  }

  private static boolean equals(final Object lhs, final Object rhs) {
    return null == lhs ? null == rhs : lhs.equals(rhs);
  }
}
