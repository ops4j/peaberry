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

package org.ops4j.peaberry.util;

import static org.ops4j.peaberry.util.StaticImport.unavailable;

import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;

/**
 * A simple mutable {@link Export} built on top of a single {@link Import}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class SimpleExport<T>
    implements Export<T> {

  private Map<String, ?> attributes;
  private volatile Import<T> service;

  /**
   * Create a new {@link Export} using the given {@link Import} details.
   * 
   * @param service service to be exported
   */
  public SimpleExport(final Import<T> service) {
    this.service = service;
  }

  public T get() {
    return service.get();
  }

  public synchronized Map<String, ?> attributes() {
    final Map<String, ?> liveAttributes = service.attributes();
    // can override attributes but must honour the original service availability
    return null == attributes || null == liveAttributes ? liveAttributes : attributes;
  }

  public void unget() {
    service.unget();
  }

  public synchronized void put(final T newInstance) {
    unput();

    if (null != newInstance) {
      // override original service with new fixed instance
      service = new StaticImport<T>(newInstance, attributes);
    }
  }

  public synchronized void attributes(final Map<String, ?> newAttributes) {
    // override original map
    attributes = newAttributes;
  }

  public synchronized void unput() {
    if (null == attributes) {
      // perform lazy backup if needed
      attributes = service.attributes();
    }
    service = unavailable();
  }
}
