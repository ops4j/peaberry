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

package org.ops4j.peaberry.internal;

import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;

/**
 * A simple mutable {@link Export}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class SimpleExport<T>
    implements Export<T> {

  private static final Import<?> UNAVAILABLE = new StaticImport<Object>(null, null);

  private Map<String, ?> attributes;
  private Import<T> service;

  SimpleExport(final Import<T> service) {
    // cache original service metadata
    attributes = service.attributes();
    this.service = service;
  }

  public T get() {
    return service.get();
  }

  public Map<String, ?> attributes() {
    // allow overriding, but still honour service availability
    return null == service.attributes() ? null : attributes;
  }

  public void unget() {
    service.unget();
  }

  public void put(final T newInstance) {
    // override original service with new static instance
    service = new StaticImport<T>(newInstance, attributes);
  }

  public void attributes(final Map<String, ?> newAttributes) {
    // override original metadata
    attributes = newAttributes;
  }

  @SuppressWarnings("unchecked")
  public void unput() {
    service = (Import) UNAVAILABLE;
  }
}
