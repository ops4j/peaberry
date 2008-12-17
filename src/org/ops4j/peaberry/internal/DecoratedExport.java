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

import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * An {@link Export} whose <i>import</i> aspect is decorated by the given
 * {@link ImportDecorator}. No decoration is applied when <i>exporting</i>.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class DecoratedExport<T>
    implements Export<T> {

  private final Export<T> originalExport;
  private final Import<T> decoratedImport;

  public DecoratedExport(final Export<T> originalExport, final ImportDecorator<? super T> decorator) {
    this.originalExport = originalExport;
    this.decoratedImport = decorator.decorate(originalExport);
  }

  // Export aspect...

  public void put(final T instance) {
    originalExport.put(instance);
  }

  public void attributes(final Map<String, ?> attributes) {
    originalExport.attributes(attributes);
  }

  public void unput() {
    originalExport.unput();
  }

  // Import aspect...

  public T get() {
    return decoratedImport.get();
  }

  public Map<String, ?> attributes() {
    return decoratedImport.attributes();
  }

  public void unget() {
    decoratedImport.unget();
  }
}
