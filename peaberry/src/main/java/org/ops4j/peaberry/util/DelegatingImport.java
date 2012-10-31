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

import java.util.Map;

import org.ops4j.peaberry.Import;

/**
 * A simple {@link Import} that always delegates to another {@link Import}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class DelegatingImport<T>
    implements Import<T> {

  private final Import<T> service;

  /**
   * Create a new {@link Import} that delegates to the given {@link Import}.
   * 
   * @param service delegate service
   */
  protected DelegatingImport(final Import<T> service) {
    this.service = service;
  }

  public T get() {
    return service.get();
  }

  public Map<String, ?> attributes() {
    return service.attributes();
  }

  public void unget() {
    service.unget();
  }

  public boolean available() {
    return service.available();
  }
}
