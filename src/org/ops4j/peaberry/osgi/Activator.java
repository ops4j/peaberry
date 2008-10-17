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

import static com.google.inject.name.Names.named;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;
import static org.osgi.framework.Bundle.ACTIVE;
import static org.osgi.framework.Bundle.STARTING;

import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;

/**
 * OSGi {@code BundleActivator} that manages a cleanup thread for peaberry.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 * @author rinsvind@gmail.com (Todor Boev)
 */
public final class Activator
    implements BundleActivator {

  /**
   * OSGi property controlling how quickly cached service instances are flushed.
   */
  private static final String FLUSH_INTERVAL_KEY = "org.ops4j.peaberry.osgi.flushInterval";

  /**
   * Cleans up registered {@code CachingServiceRegistry}s at a fixed interval.
   */
  protected static final class ImportManager
      implements Runnable {

    private final Iterable<CachingServiceRegistry> registries;
    private final int flushInterval;
    private final Bundle bundle;

    @Inject
    public ImportManager(final Iterable<CachingServiceRegistry> registries,
        @Named(FLUSH_INTERVAL_KEY) final int flushInterval, final BundleContext ctx) {

      this.registries = registries;
      this.flushInterval = flushInterval;
      this.bundle = ctx.getBundle();
    }

    public void run() {
      do {
        try {
          Thread.sleep(flushInterval);
        } catch (final InterruptedException e) {/* wake-up */} // NOPMD

        // flush out any unused cached service instances...
        for (final CachingServiceRegistry i : registries) {
          try {
            i.flush();
          } catch (final ServiceUnavailableException e) {/* already gone */} // NOPMD
        }
      } while ((bundle.getState() & (STARTING | ACTIVE)) != 0);
    }
  }

  private Thread cleanupThread;

  public void start(final BundleContext ctx) {

    final Injector injector = Guice.createInjector(osgiModule(ctx), new AbstractModule() {
      @Override
      protected void configure() {

        // retrieve current flush setting from the OSGi framework
        bindConstant().annotatedWith(named(FLUSH_INTERVAL_KEY)).to(
            osgiProperty(FLUSH_INTERVAL_KEY, "8000"));

        // eat our own cat-food: lookup registered caching registries from OSGi
        bind(new TypeLiteral<Iterable<CachingServiceRegistry>>() {}).toProvider(
            service(CachingServiceRegistry.class).multiple());
      }

      private String osgiProperty(final String name, final String defaultValue) {
        final String value = ctx.getProperty(name);
        return null == value ? defaultValue : value;
      }
    });

    if (injector.getInstance(Key.get(int.class, named(FLUSH_INTERVAL_KEY))) >= 0) {
      cleanupThread = new Thread(injector.getInstance(ImportManager.class));
      cleanupThread.setDaemon(true);
      cleanupThread.start();
    }
  }

  public void stop(@SuppressWarnings("unused") final BundleContext ctx) {

    if (null != cleanupThread) {
      cleanupThread.interrupt();
      cleanupThread = null;
    }
  }
}
