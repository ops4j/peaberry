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
import java.util.concurrent.Callable;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceException;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * An {@code ImportDecorator} that caches the first valid service instance and
 * uses that until it becomes invalid. The decorator then calls the reset task
 * to see if it should reset, or throw {@link ServiceUnavailableException}.
 * <p>
 * NOTE: a sticky decorator only makes sense for "single" injected services.
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
    private T instance;

    StickyImport(final Import<T> handle) {
      this.handle = handle;
    }

    public synchronized T get() {

      if (null != resetTask && null != instance && null == handle.attributes()) {
        try {
          if (resetTask.call()) {
            instance = null;
            handle.unget();
          }
        } catch (final Exception e) {
          throw new ServiceException("Exception in resetTask", e);
        }
      }

      if (null == instance) {
        try {
          instance = handle.get();
        } catch (final RuntimeException re) {
          handle.unget();
          throw re;
        }
      }

      return instance;
    }

    public synchronized Map<String, ?> attributes() {
      return instance == null ? null : handle.attributes();
    }

    public void unget() {/* nothing to do */}
  }

  public <T extends S> Import<T> decorate(final Import<T> handle) {
    return new StickyImport<T>(handle);
  }
}
