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

package org.ops4j.peaberry.eclipse;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Import;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class IterableExtension<T>
    implements Iterable<Import<T>> {

  final ExtensionListener listener;
  final AttributeFilter filter;

  public IterableExtension(final ExtensionListener listener, final AttributeFilter filter) {
    this.listener = listener;
    this.filter = filter;
  }

  public Iterator<Import<T>> iterator() {
    return new Iterator<Import<T>>() {

      // keep track of where we've been...
      private ExtensionImport prevImport;
      private ExtensionImport nextImport;

      public boolean hasNext() {
        return findNextImport() != null;
      }

      @SuppressWarnings("unchecked")
      public Import<T> next() {

        if (null == findNextImport()) {
          throw new NoSuchElementException();
        }

        // used cached result
        prevImport = nextImport;
        nextImport = null;

        return (Import) prevImport;
      }

      private ExtensionImport findNextImport() {
        if (null == nextImport) {

          // based on our last result and the current list, find next result...
          final ExtensionImport tempImport = listener.findNextImport(prevImport, filter);

          // ...and cache it
          if (null != tempImport) {
            prevImport = null;
            nextImport = tempImport;
          }
        }

        return nextImport;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
