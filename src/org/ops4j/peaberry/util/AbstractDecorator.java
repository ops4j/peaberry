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

package org.ops4j.peaberry.util;

import java.util.Map;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Skeletal implementation to simplify development of {@link ImportDecorator}s.
 * <p>
 * Developers only have to extend this class and provide an implementation of
 * the {@link #decorate(Object, Map)} method, which takes the original service
 * instance and associated attribute map, and returns a decorated instance.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public abstract class AbstractDecorator<S>
    implements ImportDecorator<S> {

  public final <T extends S> Import<T> decorate(final Import<T> service) {
    return new DelegatingImport<T>(service) {

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        return (T) decorate(service.get(), service.attributes());
      }
    };
  }

  /**
   * Decorate the current service instance.
   * 
   * @param instance service instance
   * @param attributes service attributes
   * @return decorated service instance
   */
  protected abstract S decorate(S instance, Map<String, ?> attributes);
}
