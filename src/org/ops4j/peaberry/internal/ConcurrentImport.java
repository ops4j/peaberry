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

import java.util.Iterator;
import java.util.Map;

import org.ops4j.peaberry.Import;

/**
 * Provide an {@link Import} that dynamically delegates to the best service but
 * also tracks its use (even across multiple threads) so that unget() is always
 * called on the same service handle as get() was originally.
 * 
 * The solution below uses the same handle until no threads are actively using
 * the injected instance. This might keep a service in use for a little longer
 * than expected when there is heavy contention, but it doesn't require use of
 * any thread locals or additional context stacks.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ConcurrentImport<T>
    implements Import<T> {

  private final Iterable<Import<T>> services;

  private Import<T> service;
  private T instance;
  private int count;

  ConcurrentImport(final Iterable<Import<T>> services) {
    this.services = services;
  }

  // need barrier on entry...
  public synchronized T get() {
    count++;
    if (null == service) {
      // first valid service handle may appear at any time
      final Iterator<Import<T>> i = services.iterator();
      if (i.hasNext()) {
        service = i.next();
        instance = service.get(); // only called once
      }
    }
    return instance;
  }

  public synchronized Map<String, ?> attributes() {
    if (null == service) {
      // no service in use, fall-back to dynamic query
      final Iterator<Import<T>> i = services.iterator();
      return i.hasNext() ? i.next().attributes() : null;
    }
    return service.attributes();
  }

  public synchronized void unget() {
    // last thread to exit does the unget...
    if (0 == --count && null != service) {
      final Import<T> temp = service;
      instance = null;
      service = null;
      temp.unget();
    }
  }
}
