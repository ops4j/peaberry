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

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;

/**
 * A basic mutable {@link Export} derived from a single dynamic {@link Import}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class SimpleExport<T>
    implements Export<T> {

  // track usage of the original dynamic service so we can unwind it later
  private static final class CountingImport<T>
      extends DelegatingImport<T> {

    private int count;

    CountingImport(final Import<T> service) {
      super(service);
    }

    @Override
    public T get() {
      count++;
      return super.get();
    }

    @Override
    public void unget() {
      count--;
      super.unget();
    }

    void unwind() {
      // service swapped while still in use, so balance the surplus of "gets"
      while (count-- > 0) {
        super.unget();
      }
    }
  }

  private Import<T> service;
  private Map<String, ?> attributes;

  /**
   * Create a new {@link Export} from the given {@link Import}.
   * 
   * @param service service being exported
   */
  public SimpleExport(final Import<T> service) {
    this.service = new CountingImport<T>(service);
  }

  public synchronized T get() {
    return service.get();
  }

  public synchronized Map<String, ?> attributes() {
    return null == attributes ? service.attributes() : attributes;
  }

  public synchronized void unget() {
    service.unget();
  }

  public boolean available() {
    return service.available();
  }

  public synchronized void put(final T newInstance) {
    // might need to balance the gets + ungets
    if (service instanceof CountingImport<?>) {
      ((CountingImport<?>) service).unwind();
    }

    service = new StaticImport<T>(newInstance, service.attributes());
  }

  public synchronized void attributes(final Map<String, ?> newAttributes) {
    attributes = newAttributes;
  }

  public void unput() {
    put(null); // simple alias
  }
}
