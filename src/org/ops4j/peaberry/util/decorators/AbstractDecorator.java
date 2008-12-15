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

package org.ops4j.peaberry.util.decorators;

import java.util.Map;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Skeletal implementation to simplify development of {@link ImportDecorator}s.
 * Programmers only need to implement the {@code decorate(T instance)} method.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public abstract class AbstractDecorator<S>
    implements ImportDecorator<S> {

  public <T extends S> Import<T> decorate(final Import<T> service) {
    return new Import<T>() {

      public T get() {
        return decorate(service.get());
      }

      public Map<String, ?> attributes() {
        return decorate(service.attributes());
      }

      public void unget() {
        service.unget();
      }
    };
  }

  /**
   * Decorate the current service instance.
   * 
   * @param instance service instance
   * @return decorated service instance
   */
  protected abstract <T extends S> T decorate(T instance);

  /**
   * Decorate the current service attributes, defaults to no decoration.
   * 
   * @param attributes service attributes
   * @return decorated service attributes
   */
  protected Map<String, ?> decorate(final Map<String, ?> attributes) {
    return attributes;
  }
}
