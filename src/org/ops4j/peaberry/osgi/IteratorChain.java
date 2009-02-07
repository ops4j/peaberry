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

import org.ops4j.peaberry.Import;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class IteratorChain<T>
    implements Iterator<Import<T>> {

  private final Iterable<Import<T>>[] iterables;

  public IteratorChain(final Iterable<Import<T>>[] iterables) {
    this.iterables = iterables.clone();
  }

  private int nextIterableIndex = 0;
  private Iterator<Import<T>> i;

  public boolean hasNext() {
    do {
      if (nextIterableIndex > 0 && i.hasNext()) {
        return true;
      } else if (nextIterableIndex < iterables.length) {
        i = iterables[nextIterableIndex++].iterator();
      } else {
        return false;
      }
    } while (true);
  }

  public Import<T> next() {
    do {
      if (nextIterableIndex > 0 && i.hasNext()) {
        return i.next();
      } else if (nextIterableIndex < iterables.length) {
        i = iterables[nextIterableIndex++].iterator();
      } else {
        throw new NoSuchElementException();
      }
    } while (true);
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}
