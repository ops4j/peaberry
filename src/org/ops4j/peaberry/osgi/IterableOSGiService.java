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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Import;

/**
 * Filtered iterable view over a collection of @{code OSGiServiceImport}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class IterableOSGiService<T>
    implements Iterable<Import<T>> {

  final OSGiServiceListener listener;
  final AttributeFilter filter;

  public IterableOSGiService(final OSGiServiceListener listener, final AttributeFilter filter) {

    this.listener = listener;
    this.filter = filter;
  }

  public Iterator<Import<T>> iterator() {
    return new Iterator<Import<T>>() {

      private OSGiServiceImport prevImport;
      private OSGiServiceImport nextImport;

      public boolean hasNext() {
        return findNextImport() != null;
      }

      @SuppressWarnings("unchecked")
      public Import<T> next() {

        if (null == findNextImport()) {
          throw new NoSuchElementException();
        }

        prevImport = nextImport;
        nextImport = null;

        return (Import) prevImport;
      }

      private OSGiServiceImport findNextImport() {
        if (null == nextImport) {

          final OSGiServiceImport tempImport = listener.findNextImport(prevImport, filter);

          if (null != tempImport) {
            prevImport = null;
            nextImport = tempImport;
          }
        }

        return nextImport;
      }

      // /CLOVER:OFF
      public void remove() {
        throw new UnsupportedOperationException();
      }
      // /CLOVER:ON
    };
  }
}
