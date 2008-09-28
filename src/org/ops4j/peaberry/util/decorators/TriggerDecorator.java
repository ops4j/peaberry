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
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public abstract class TriggerDecorator<S>
    implements ImportDecorator<S> {

  protected abstract <T> T trigger(T thisInstance, T nextInstance);

  public <T extends S> Import<T> decorate(final Import<T> handle) {
    return new Import<T>() {

      private T prevInstance;
      private T thisInstance;

      public synchronized T get() {
        final T nextInstance = handle.get();
        if (nextInstance != prevInstance) {
          thisInstance = trigger(thisInstance, nextInstance);
          prevInstance = nextInstance;
        }
        return thisInstance;
      }

      public Map<String, ?> attributes() {
        return handle.attributes();
      }

      public void unget() {
        handle.unget();
      }
    };
  }
}
