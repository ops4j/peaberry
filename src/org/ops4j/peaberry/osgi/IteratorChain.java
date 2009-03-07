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

package org.ops4j.peaberry.osgi;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} that iterates over a series of iterators in turn.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class IteratorChain<T>
    implements Iterator<T> {

  private final Iterable<T>[] lazyIterables;
  private final Iterator<T>[] iterators;

  @SuppressWarnings("unchecked")
  IteratorChain(final Iterable<T>[] lazyIterables) {
    this.lazyIterables = lazyIterables.clone();
    iterators = new Iterator[lazyIterables.length];
  }

  private int index;

  public boolean hasNext() {
    // peek ahead, but don't disturb current position
    for (int i = index; i < iterators.length; i++) {
      unroll(i);
      if (iterators[i].hasNext()) {
        return true;
      }
    }
    return false;
  }

  public T next() {
    // move forwards along the chain
    while (index < iterators.length) {
      unroll(index);
      try {
        // is this section finished yet?
        return iterators[index].next();
      } catch (final NoSuchElementException e) {
        index++; // move onto next section
      }
    }
    throw new NoSuchElementException();
  }

  private void unroll(final int i) {
    if (null == iterators[i]) {
      iterators[i] = lazyIterables[i].iterator();
    }
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}
