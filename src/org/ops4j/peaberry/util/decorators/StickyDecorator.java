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

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * An {@link ImportDecorator} that caches the first valid service instance and
 * uses that until it becomes invalid. The decorator then calls the reset task
 * to see if it should reset the cache and get a new service instance.
 * <p>
 * If no reset task is provided, the service instance cache is never reset.
 * <p>
 * Note: sticky decorators only really make sense for <i>single</i> services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class StickyDecorator<S>
    implements ImportDecorator<S> {

  final Callable<Boolean> resetTask;

  public StickyDecorator(final Callable<Boolean> resetTask) {
    this.resetTask = resetTask;
  }

  private final class StickyImport<T>
      implements Import<T> {

    private final Import<T> handle;
    private boolean reset = true;
    private WeakReference<T> instanceRef;

    StickyImport(final Import<T> handle) {
      this.handle = handle;
    }

    private T instance() {
      return null == instanceRef ? null : instanceRef.get();
    }

    public synchronized T get() {

      // use attributes() to detect when the current service instance is invalid
      if (null != resetTask && null != instanceRef && null == attributes()) {

        // always clear the current service once it's invalid
        instanceRef.clear();

        try {
          // should we reset and take the next valid service?
          reset = resetTask.call();
        } catch (final Exception e) {
          throw new ServiceException("Exception in resetTask", e);
        }

        if (reset) {
          handle.unget(); // balance previous successful get
        }
      }

      if (reset) {
        try {
          final T service = handle.get();
          instanceRef = new WeakReference<T>(service);
          reset = null == service;
        } finally {
          if (reset) {
            instanceRef = null;
            handle.unget(); // balance previous unsuccessful get
          }
        }
      }

      return instance();
    }

    public synchronized Map<String, ?> attributes() {
      return null == instance() ? null : handle.attributes();
    }

    public void unget() {/* nothing to do */}
  }

  public <T extends S> Import<T> decorate(final Import<T> handle) {
    return new StickyImport<T>(handle);
  }
}
