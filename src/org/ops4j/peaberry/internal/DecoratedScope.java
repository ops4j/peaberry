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
final class DecoratedScope<S>
    implements ServiceScope<S> {

  private final ImportDecorator<? super S> decorator;
  private final ServiceScope<? super S> scope;

  DecoratedScope(final ImportDecorator<? super S> decorator, final ServiceScope<? super S> scope) {
    this.decorator = decorator;
    this.scope = scope;
  }

  public <T extends S> Export<T> add(final Import<T> service) {

    // wrap service to allow updates, decorate the wrapper, then publish
    final Export<T> backingService = new BackingExport<T>(service);
    final Import<T> decoratedService = decorator.decorate(backingService);
    final Export<T> publishedService = scope.add(decoratedService);

    return new Export<T>() {

      // Export aspect... (ensure decoration of new instances/attributes)

      public synchronized void put(T instance) {
        // force decoration of new instance
        backingService.put(instance);
        publishedService.put(decoratedService.get());
      }

      public synchronized void attributes(Map<String, ?> attributes) {
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

  // allow update of decorated service under the covers
  private static class BackingExport<T>
      implements Export<T> {

    private static final Import<?> UNAVAILABLE = new StaticImport<Object>(null, null);

    private Map<String, ?> attributes;
    private Import<T> service;

    BackingExport(final Import<T> service) {
      // cache original service metadata
      attributes = service.attributes();
      this.service = service;
    }

    public T get() {
      return service.get();
    }

    public Map<String, ?> attributes() {
      // allow metadata overriding, tied to service availability
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
}
