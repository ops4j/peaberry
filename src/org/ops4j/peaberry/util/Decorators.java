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

package org.ops4j.peaberry.util;

import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.util.decorators.StickyDecorator;

import com.google.inject.Key;

/**
 * Provide keys to various useful generic {@code ImportDecorator}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Decorators {

  // instances not allowed
  private Decorators() {}

  /**
   * @return decorator that caches the service instance until told otherwise.
   */
  @SuppressWarnings("unchecked")
  public static <S> Key<? extends ImportDecorator<S>> sticky() {
    return (Key) Key.get(StickyDecorator.class);
  }
}
