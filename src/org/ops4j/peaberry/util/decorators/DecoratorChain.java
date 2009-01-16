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

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * An {@link ImportDecorator} that applies decorators in a chain, right-left.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class DecoratorChain<S>
    implements ImportDecorator<S> {

  private final ImportDecorator<S>[] decorators;

  public DecoratorChain(final ImportDecorator<S>... decorators) {
    this.decorators = decorators.clone();
  }

  public <T extends S> Import<T> decorate(final Import<T> handle) {
    Import<T> h = handle;
    for (int i = decorators.length - 1; i >= 0; i--) {
      h = decorators[i].decorate(h);
    }
    return h;
  }
}
