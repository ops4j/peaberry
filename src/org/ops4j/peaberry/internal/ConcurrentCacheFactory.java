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
import static jsr166y.ConcurrentReferenceHashMap.ReferenceType.SOFT;
import static jsr166y.ConcurrentReferenceHashMap.ReferenceType.WEAK;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentMap;

import jsr166y.ConcurrentReferenceHashMap;
import jsr166y.ConcurrentReferenceHashMap.Option;

/**
 * Provide instances of the proposed JSR166 {@link ConcurrentReferenceHashMap}.
 * 
 * @see http://anonsvn.jboss.org/repos/jbosscache/experimental/jsr166/src/jsr166y
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ConcurrentCacheFactory {

  // instances not allowed
  private ConcurrentCacheFactory() {}

  private static final EnumSet<Option> IDENTITY = EnumSet.of(IDENTITY_COMPARISONS);

  // usual defaults
  private static final int CAPACITY = 16;
  private static final float LOAD = 0.75f;
  private static final int CONCURRENCY = 16;

  /**
   * @return soft-cache with referential-equality semantics
   */
  public static <K, V> ConcurrentMap<K, V> newSoftCache() {
    return new ConcurrentReferenceHashMap<K, V>(CAPACITY, LOAD, CONCURRENCY, WEAK, SOFT, IDENTITY);
  }

  /**
   * @return weak-cache with referential-equality semantics
   */
  public static <K, V> ConcurrentMap<K, V> newWeakCache() {
    return new ConcurrentReferenceHashMap<K, V>(CAPACITY, LOAD, CONCURRENCY, WEAK, WEAK, IDENTITY);
  }
}
