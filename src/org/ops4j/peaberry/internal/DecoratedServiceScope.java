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
import org.ops4j.peaberry.ServiceScope;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * A {@link ServiceScope} decorated by the given {@link ImportDecorator}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class DecoratedServiceScope<S>
    implements ServiceScope<S> {

  private final ImportDecorator<? super S> decorator;
  private final ServiceScope<? super S> scope;

  DecoratedServiceScope(final ImportDecorator<? super S> decorator,
      final ServiceScope<? super S> scope) {
    this.decorator = decorator;
    this.scope = scope;
  }

  public <T extends S> Export<T> add(final Import<T> service) {

    // wrap service to allow updates, decorate the wrapper, then publish
    final Export<T> backingService = new SimpleExport<T>(service);
    final Import<T> decoratedService = decorator.decorate(backingService);
    final Export<T> publishedService = scope.add(decoratedService);

    return new Export<T>() {

      // Export aspect... (ensure decoration of new instances/attributes)

      public synchronized void put(final T instance) {
        // force decoration of new instance
        backingService.put(instance);
        publishedService.put(decoratedService.get());
      }

      public synchronized void attributes(final Map<String, ?> attributes) {
        // force decoration of new attributes
        backingService.attributes(attributes);
        publishedService.attributes(decoratedService.attributes());
      }

      public synchronized void unput() {
        try {
          publishedService.unput();
        } finally {
          // clear wrapped service
          backingService.unput();
        }
      }

      // Import aspect... (retrieve the decorated instances/attributes)

      public T get() {
        return publishedService.get();
      }

      public Map<String, ?> attributes() {
        return publishedService.attributes();
      }

      public void unget() {
        publishedService.unget();
      }
    };
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof DecoratedServiceScope) {
      final DecoratedServiceScope<?> decoratedScope = (DecoratedServiceScope<?>) rhs;
      return equals(decorator, decoratedScope.decorator) && equals(scope, decoratedScope.scope);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (null == decorator ? 0 : decorator.hashCode()) ^ (null == scope ? 0 : scope.hashCode());
  }

  private static boolean equals(final Object lhs, final Object rhs) {
    return null == lhs ? null == rhs : lhs.equals(rhs);
  }
}
