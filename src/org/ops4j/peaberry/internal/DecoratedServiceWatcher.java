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
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.util.DelegatingImport;
import org.ops4j.peaberry.util.SimpleExport;

/**
 * A {@link ServiceWatcher} decorated by the given {@link ImportDecorator}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class DecoratedServiceWatcher<S>
    implements ServiceWatcher<S> {

  private final ImportDecorator<? super S> decorator;
  private final ServiceWatcher<? super S> watcher;

  DecoratedServiceWatcher(final ImportDecorator<? super S> decorator,
      final ServiceWatcher<? super S> watcher) {
    this.decorator = decorator;
    this.watcher = watcher;
  }

  public <T extends S> Export<T> add(final Import<T> service) {

    // wrap service to allow updates, decorate the wrapper, then publish
    final Export<T> original = new SimpleExport<T>(service);
    final Import<T> decorated = decorator.decorate(original);
    final Export<T> published = watcher.add(decorated);

    // watcher is not interested!
    if (null == published) {
      return null;
    }

    return new DecoratedExport<T>(original, decorated, published);
  }

  private static final class DecoratedExport<T>
      extends DelegatingImport<T>
      implements Export<T> {

    private final Export<T> original;
    private final Import<T> decorated;
    private final Export<T> published;

    DecoratedExport(final Export<T> original, final Import<T> decorated, final Export<T> published) {
      super(original);

      this.original = original;
      this.decorated = decorated;
      this.published = published;
    }

    public synchronized void put(final T newInstance) {
      // force decoration of new instance
      original.put(newInstance);
      published.put(null == newInstance ? null : decorated.get());
    }

    public synchronized void attributes(final Map<String, ?> attributes) {
      // force decoration of new attributes
      original.attributes(attributes);
      published.attributes(decorated.attributes());
    }

    public void unput() {
      put(null); // simple alias
    }
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof DecoratedServiceWatcher) {
      final DecoratedServiceWatcher<?> decoratedWatcher = (DecoratedServiceWatcher<?>) rhs;
      return decorator.equals(decoratedWatcher.decorator)
          && watcher.equals(decoratedWatcher.watcher);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return decorator.hashCode() ^ watcher.hashCode();
  }
}
