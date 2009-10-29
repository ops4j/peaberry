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

package org.ops4j.peaberry.util;

import static java.util.Collections.unmodifiableMap;

import java.util.Map;

import org.ops4j.peaberry.Import;

/**
 * An {@link Import} consisting of a fixed instance and optional attribute map.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class StaticImport<T>
    implements Import<T> {

  private final T instance;
  private final Map<String, ?> attributes;

  /**
   * Create a static {@link Import} for the given instance.
   * 
   * @param instance service instance
   */
  public StaticImport(final T instance) {
    this.instance = instance;
    this.attributes = null;
  }

  /**
   * Create a static {@link Import} for the given instance and attribute map.
   * 
   * @param instance service instance
   * @param attributes service attributes
   */
  public StaticImport(final T instance, final Map<String, ?> attributes) {
    this.instance = instance;
    this.attributes = null == attributes ? null : unmodifiableMap(attributes);
  }

  public T get() {
    return instance;
  }

  public Map<String, ?> attributes() {
    return attributes;
  }

  public void unget() {/* nothing to do */}

  public boolean available() {
    return null != instance;
  }
}
