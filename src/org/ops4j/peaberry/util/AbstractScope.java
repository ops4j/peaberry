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
import org.ops4j.peaberry.ServiceScope;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Skeletal implementation to simplify development of {@link ServiceScope}s.
 * <p>
 * Developers only have to extend this class and provide implementations of the
 * {@code adding} {@code modified} and {@code removed} service tracking methods.
 * The design is loosely based on the OSGi {@link ServiceTrackerCustomizer}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public abstract class AbstractScope<S>
    implements ServiceScope<S> {

  @SuppressWarnings("unchecked")
  public <T extends S> Export<T> add(final Import<T> service) {
    return (Export) new TrackingExport((Import) service);
  }

  // simple instance + attributes holder
  private static class SimpleImport<T>
      implements Import<T> {

    T instance;
    Map<String, ?> attributes;

    public SimpleImport(final T instance, final Map<String, ?> attributes) {
      this.instance = instance;
      this.attributes = attributes;
    }

    public synchronized T get() {
      return instance;
    }

    public synchronized Map<String, ?> attributes() {
      return attributes;
    }

    public void unget() {/* nothing to do */}
  }

  private final class TrackingExport
      extends SimpleImport<S>
      implements Export<S> {

    public TrackingExport(final Import<S> service) {
      super(adding(service), service.attributes());
    }

    public synchronized void put(final S newInstance) {
      if (null != instance) {
        removed(instance);
      }
      instance = adding(new SimpleImport<S>(newInstance, attributes));
    }

    public synchronized void attributes(final Map<String, ?> newAttributes) {
      attributes = newAttributes;
      if (null != instance) {
        modified(instance, attributes);
      }
    }

    public synchronized void unput() {
      if (null != instance) {
        removed(instance);
        instance = null;
      }
    }
  }

  /**
   * Notification that a service has been added to this scope.
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
   * Notification that a service has been removed from this scope.
   * 
   * @param instance tracking instance
   */
  protected void removed(final S instance) {} // NOPMD
}
