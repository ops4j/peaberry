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

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Filtered iterable view over a collection of OSGi @{code ServiceReference}s.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class IterableOSGiService<T>
    implements Iterable<Import<T>> {

  final BundleContext bundleContext;
  final Class<? extends T> type;
  final OSGiServiceListener listener;
  final AttributeFilter filter;

  public IterableOSGiService(final BundleContext bundleContext, final OSGiServiceListener listener,
      final Class<? extends T> type, final AttributeFilter filter) {

    this.bundleContext = bundleContext;
    this.listener = listener;
    this.type = type;
    this.filter = filter;
  }

  public Iterator<Import<T>> iterator() {
    return new Iterator<Import<T>>() {

      private Import<T> nextImport;
      private ServiceReference ref;

      public boolean hasNext() {
        findNextImport();
        return nextImport != null;
      }

      public Import<T> next() {
        findNextImport();
        if (null == nextImport) {
          throw new ServiceUnavailableException();
        }

        final Import<T> tempImport = nextImport;

        nextImport = null;
        return tempImport;
      }

      private void findNextImport() {
        if (null == nextImport) {
          final ServiceReference nextRef = listener.findNextService(ref, filter);
          if (nextRef != null) {
            nextImport = new OSGiServiceImport<T>(bundleContext, type, nextRef);
            ref = nextRef;
          }
        }
      }

      // /CLOVER:OFF
      public void remove() {
        throw new UnsupportedOperationException();
      }
      // /CLOVER:ON
    };
  }
}
