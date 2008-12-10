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

import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.util.decorators.ChainedDecorator;
import org.ops4j.peaberry.util.decorators.StickyDecorator;

/**
 * Methods for creating various general purpose {@link ImportDecorator}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Decorators {

  // instances not allowed
  private Decorators() {}

  /**
   * An {@link ImportDecorator} that caches the first valid service instance and
   * uses that until it becomes invalid. The decorator then calls the reset task
   * to see if it should reset, or throw {@link ServiceUnavailableException}.
   * <p>
   * NOTE: a sticky decorator only makes sense for "single" injected services.
   * 
   * @param resetTask called when cached service becomes invalid, may be null
   * @return decorator that caches service instances
   */
  public static <S> ImportDecorator<S> sticky(final Callable<Boolean> resetTask) {
    return new StickyDecorator<S>(resetTask);
  }

  /**
   * An {@link ImportDecorator} that combines several decorators in a chain.
   * 
   * @param decorators sequence of decorators
   * @return decorator that combines given decorators
   */
  public static <S> ImportDecorator<S> chained(final ImportDecorator<S>... decorators) {
    return new ChainedDecorator<S>(decorators);
  }
}
