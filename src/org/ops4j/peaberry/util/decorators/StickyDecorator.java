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

import java.util.concurrent.Callable;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * {@code ImportDecorator} that caches service instances until told otherwise.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class StickyDecorator<S>
    implements ImportDecorator<S> {

  static final ServiceUnavailableException NO_SERVICE = new ServiceUnavailableException();

  final Callable<Boolean> resetTask;

  public StickyDecorator(final Callable<Boolean> resetTask) {
    this.resetTask = resetTask;
  }

  public <T extends S> Import<T> decorate(final Import<T> handle) {
    return new Import<T>() {

      // sticky service
      private volatile T instance;

      public T get() {
        // DCL is safe in Java5+
        if (null == instance) {
          synchronized (this) {
            if (null == instance) {
              changeService();
            }
          }
        }
        if (null == instance) {
          throw NO_SERVICE;
        }
        if (null != resetTask) {
          checkService();
        }
        return instance;
      }

      private synchronized void checkService() {
        try {
          instance.hashCode();
        } catch (final RuntimeException re) {
          try {
            if (resetTask.call()) {
              changeService();
            }
          } catch (final Exception e) {}
        }
      }

      private void changeService() {
        if (null != instance) {
          handle.unget();
        }
        try {
          instance = handle.get();
        } catch (final RuntimeException re) {
          handle.unget();
        }
      }

      public void unget() {/* nothing to do */}
    };
  }
}
