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

import java.util.concurrent.Callable;

import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.util.decorators.StickyDecorator;

/**
 * Provide keys to various useful generic {@code ImportDecorator}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Decorators {

  // instances not allowed
  private Decorators() {}

  /**
   * An {@code ImportDecorator} that lazily caches service instances and calls
   * the given task when the cached service is no longer available. This task
   * can perform its own refresh actions, and ask for the service to be reset.
   * 
   * @param resetTask task called when the sticky service goes missing
   * @return decorator that caches service instances.
   */
  public static <S> ImportDecorator<S> sticky(final Callable<Boolean> resetTask) {
    return new StickyDecorator<S>(resetTask);
  }
}
