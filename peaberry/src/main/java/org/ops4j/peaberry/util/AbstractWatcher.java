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

package org.ops4j.peaberry.util;

import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceWatcher;

/**
 * Skeletal implementation to simplify development of {@link ServiceWatcher}s.
 * <p>
 * Developers only have to extend this class and provide implementations of the
 * {@link #adding(Import)}, {@link #modified(Object, Map)}, and {@link #removed}
 * service tracking methods. The design of this helper class is loosely based on
 * the OSGi ServiceTrackerCustomizer.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 * 
 * @since 1.1
 */
public abstract class AbstractWatcher<S>
    implements ServiceWatcher<S> {

  @SuppressWarnings("unchecked")
  public final <T extends S> Export<T> add(final Import<T> service) {
    final TrackingExport export = new TrackingExport((Import) service);
    return null == export.tracker ? null : (Export) export;
  }

  private final class TrackingExport
      extends SimpleExport<S> {

    S tracker; // customized tracker object

    TrackingExport(final Import<S> service) {
      super(service);

      tracker = adding(this);
    }

    @Override
    public synchronized void put(final S newInstance) {
      if (null != tracker) {
        removed(tracker);
        tracker = null;
      }

      super.put(newInstance);

      if (null != newInstance) {
        tracker = adding(this);
      }
    }

    @Override
    public synchronized void attributes(final Map<String, ?> newAttributes) {
      super.attributes(newAttributes);

      if (null != tracker) {
        modified(tracker, newAttributes);
      }
    }
  }

  /**
   * Notification that a service has been added to this watcher.
   * 
   * @param service new service handle
   * @return tracking instance, null if the service shouldn't be tracked
   */
  protected S adding(final Import<S> service) {
    return service.get();
  }

  /**
   * Notification that some service attributes have been modified.
   * 
   * @param instance tracking instance
   * @param attributes service attributes
   */
  protected void modified(final S instance, final Map<String, ?> attributes) {} // NOPMD

  /**
   * Notification that a service has been removed from this watcher.
   * 
   * @param instance tracking instance
   */
  protected void removed(final S instance) {} // NOPMD
}
