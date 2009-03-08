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

import static jsr166y.ConcurrentReferenceHashMap.Option.IDENTITY_COMPARISONS;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentMap;

import jsr166y.ConcurrentReferenceHashMap;
import jsr166y.ConcurrentReferenceHashMap.Option;
import jsr166y.ConcurrentReferenceHashMap.ReferenceType;

/**
 * Provide computed maps based on the JSR166 {@link ConcurrentReferenceHashMap}.
 * 
 * @see http://anonsvn.jboss.org/repos/jbosscache/experimental/jsr166/src/jsr166y
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ComputedMapFactory {

  // instances not allowed
  private ComputedMapFactory() {}

  static final EnumSet<Option> IDENTITY = EnumSet.of(IDENTITY_COMPARISONS);

  /**
   * Computed mapping API.
   */
  interface Function<K, V> {
    V compute(K key);
  }

  /**
   * @return concurrent computed map with the given reference types
   */
  static <K, V> ConcurrentMap<K, V> computedMap(final ReferenceType keyType,
      final ReferenceType valType, final int capacity, final Function<K, V> function) {
    return new ComputedMap<K, V>(keyType, valType, capacity, function);
  }

  private static final class ComputedMap<K, V>
      extends ConcurrentReferenceHashMap<K, V> {

    private static final long serialVersionUID = 1L;
    private transient final Function<K, V> function;

    public ComputedMap(final ReferenceType keyType, final ReferenceType valType,
        final int capacity, final Function<K, V> function) {

      // small level of concurrency, as most threads read
      super(capacity, 0.75f, 2, keyType, valType, IDENTITY);
      this.function = function;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(final Object key) {
      V value = super.get(key);
      if (null == value) {
        // no mapping, use key to compute a value
        final V newValue = function.compute((K) key);
        value = putIfAbsent((K) key, newValue);
        if (null == value) {
          return newValue;
        }
      }
      return value;
    }
  }
}
