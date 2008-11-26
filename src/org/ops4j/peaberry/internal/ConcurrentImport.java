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
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * Provide an import handle that dynamically delegates to the first service, but
 * also tracks its use (even across multiple threads) so that unget() is always
 * called on the same handle as get() was originally.
 * 
 * The solution below will use the same handle until no threads are actively
 * using the injected instance. This might keep a service in use for longer than
 * expected when there is heavy contention, but it doesn't require any use of
 * thread locals or additional context stacks.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ConcurrentImport<T>
    implements Import<T> {

  static final ServiceException NO_SERVICE = new ServiceUnavailableException();

  private final Iterable<Import<T>> handles;

  private Import<T> handle;
  private T instance;
  private int count;

  public ConcurrentImport(final Iterable<Import<T>> handles) {
    this.handles = handles;
  }

  // need barrier on entry...
  public synchronized T get() {
    count++;
    if (null == handle) {
      // first valid handle may appear at any time
      final Iterator<Import<T>> i = handles.iterator();
      if (i.hasNext()) {
        handle = i.next();
        instance = handle.get(); // only called once
      }
    }
    if (null == instance) {
      throw NO_SERVICE;
    }
    return instance;
  }

  public synchronized Map<String, ?> attributes() {
    return null == instance ? null : handle.attributes();
  }

  public synchronized void unget() {
    // last thread to exit does the unget...
    if (0 == --count && null != handle) {
      instance = null;
      handle.unget();
      handle = null;
    }
  }
}
