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

import static com.google.inject.util.Jsr330.named;
import static java.lang.Math.max;
import static org.ops4j.peaberry.Peaberry.CACHE_GENERATIONS_HINT;
import static org.ops4j.peaberry.Peaberry.CACHE_INTERVAL_HINT;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;
import static org.osgi.framework.Bundle.ACTIVE;
import static org.osgi.framework.Bundle.STARTING;

import javax.inject.Inject;
import javax.inject.Named;

import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.cache.AbstractServiceImport;
import org.ops4j.peaberry.cache.CachingServiceRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * OSGi {@link BundleActivator} that manages a cleanup thread for peaberry.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 * @author rinsvind@gmail.com (Todor Boev)
 */
public final class Activator
    implements BundleActivator {

  /**
   * Default to one minute between flushes of the service cache.
   */
  private static final String CACHE_INTERVAL_DEFAULT = "60000";

  /**
   * Default to three flushes before unused services are released.
   */
  private static final String CACHE_GENERATIONS_DEFAULT = "3";

  /**
   * Cleans up registered {@link CachingServiceRegistry}s at a fixed interval.
   */
  public static class ImportManager
      implements Runnable {

    private final Bundle bundle;
    private final int interval;
    private final int generations;

    // dynamic list of currently active caching registries
    private final Iterable<CachingServiceRegistry> registries;

    @Inject
    public ImportManager(final BundleContext context,
        @Named(CACHE_INTERVAL_HINT) final int interval,
        @Named(CACHE_GENERATIONS_HINT) final int generations,
        final Iterable<CachingServiceRegistry> registries) {

      bundle = context.getBundle();

      this.interval = max(100, interval);
      this.generations = max(1, generations);
      this.registries = registries;
    }

    public final void run() {
      int gen = 0;

      do {
        // generation was flushed, safe to re-use
        AbstractServiceImport.setCacheGeneration(gen);

        try {
          Thread.sleep(interval);
        } catch (final InterruptedException e) {/* wake-up */} // NOPMD

        // rotate to the next generation
        gen = (gen + 1) % generations;

        // flush out any unused services in this generation
        for (final CachingServiceRegistry i : registries) {
          try {
            i.flush(gen);
          } catch (final ServiceUnavailableException e) {/* already gone */} // NOPMD
        }
      } while ((bundle.getState() & (STARTING | ACTIVE)) != 0);
    }
  }

  private Thread cleanupThread;

  public void start(final BundleContext context) {
    final Injector injector = Guice.createInjector(osgiModule(context), new AbstractModule() {
      @Override
      protected void configure() {

        bindConstant().annotatedWith(named(CACHE_INTERVAL_HINT)).to(
            osgiProperty(CACHE_INTERVAL_HINT, CACHE_INTERVAL_DEFAULT));

        bindConstant().annotatedWith(named(CACHE_GENERATIONS_HINT)).to(
            osgiProperty(CACHE_GENERATIONS_HINT, CACHE_GENERATIONS_DEFAULT));

        // eat our own cat-food: lookup registered caching registries from OSGi
        bind(new TypeLiteral<Iterable<CachingServiceRegistry>>() {}).toProvider(
            service(CachingServiceRegistry.class).multiple());
      }

      private String osgiProperty(final String name, final String defaultValue) {
        final String value = context.getProperty(name);
        return null == value ? defaultValue : value;
      }
    });

    // negative flush interval means no timeout, so no need to create a thread
    if (injector.getInstance(Key.get(int.class, named(CACHE_INTERVAL_HINT))) >= 0) {
      cleanupThread = new Thread(injector.getInstance(ImportManager.class), "Peaberry [cleanup]");
      cleanupThread.setPriority(Thread.MIN_PRIORITY);
      cleanupThread.setDaemon(true);
      cleanupThread.start();
    }
  }

  public void stop(final BundleContext ctx)
      throws InterruptedException {

    if (null != cleanupThread) {
      cleanupThread.interrupt();
      try {
        cleanupThread.join();
      } finally {
        cleanupThread = null;
      }
    }
  }
}
