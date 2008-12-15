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

import static java.util.Collections.enumeration;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * Lazy read-only {@link Dictionary} backed by a service attribute map.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class AttributeDictionary
    extends Dictionary<String, Object> {

  private final Map<String, Object> attributes;

  @SuppressWarnings("unchecked")
  public AttributeDictionary(final Map<String, ?> attributes) {
    this.attributes = (Map<String, Object>) attributes;
  }

  @Override
  public Object get(final Object key) {
    return attributes.get(key);
  }

  @Override
  public Enumeration<String> keys() {
    return enumeration(attributes.keySet());
  }

  @Override
  public boolean isEmpty() {
    return attributes.isEmpty();
  }

  @Override
  public int size() {
    return attributes.size();
  }

  @Override
  public Enumeration<Object> elements() {
    return enumeration(attributes.values());
  }

  @Override
  public Object put(final String key, final Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object remove(final Object key) {
    throw new UnsupportedOperationException();
  }
}
