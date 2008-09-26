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

package org.ops4j.peaberry.osgi;

import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * OSGi {@code BundleActivator} that manages a cleanup thread for peaberry.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Activator
    implements BundleActivator {

  /**
   * OSGi property controlling how quickly cached service instances are flushed.
   */
  private static final String FLUSH_INTERVAL_KEY = "org.ops4j.peaberry.osgi.flushInterval";

  // use static for cooperative stop
  static volatile Thread cleanupThread;

  /**
   * Cleans up registered {@code CachingServiceRegistry}s at a fixed interval.
   */
  protected static final class ImportManager
      implements Runnable {

    private final Iterable<CachingServiceRegistry> registries;
    private final int flushInterval;

    @Inject
    public ImportManager(final BundleContext bundleContext,
        final Iterable<CachingServiceRegistry> registries) {

      int millis;
      try {
        millis = Integer.parseInt(bundleContext.getProperty(FLUSH_INTERVAL_KEY));
      } catch (final RuntimeException e) {
        millis = 8000;
      }

      this.registries = registries;
      flushInterval = millis;
    }

    public void run() {
      do {
        try {
          Thread.sleep(flushInterval);
        } catch (final InterruptedException e) {}

        // flush out any unused cached service instances...
        for (final CachingServiceRegistry i : registries) {
          i.flush();
        }
      } while (Thread.currentThread() == cleanupThread);
    }
  }

  public void start(final BundleContext bundleContext) {

    final Injector injector = Guice.createInjector(osgiModule(bundleContext), new Module() {
      public void configure(final Binder binder) {

        // eat our own cat-food: lookup registered caching registries from OSGi
        binder.bind(Runnable.class).to(ImportManager.class).asEagerSingleton();
        binder.bind(iterable(CachingServiceRegistry.class)).toProvider(
            service(CachingServiceRegistry.class).multiple());
      }
    });

    cleanupThread = new Thread(injector.getInstance(Runnable.class));
    cleanupThread.setDaemon(true);
    cleanupThread.start();
  }

  public void stop(@SuppressWarnings("unused") final BundleContext bundleContext) {
    if (null != cleanupThread) {
      final Thread zombie = cleanupThread;

      // use cooperative stop
      cleanupThread = null;
      zombie.interrupt();
    }
  }
}
