/**
 * Copyright (C) 2009 Todor Boev
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
package org.ops4j.peaberry.activation.internal;

import java.util.HashMap;
import java.util.Map;

import org.ops4j.peaberry.activation.Constants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

import com.google.inject.Module;

/**
 * The bundle lifecycle tracking core of the extender.
 * 
 * @author Todor Boev (rinsvind@gmail.com)
 */
public class BundleTracker
    implements SynchronousBundleListener {

  private final BundleContext bc;
  private final Map<Long, BundleActivation> handlers;

  public BundleTracker(final BundleContext bc) {
    this.bc = bc;
    handlers = new HashMap<Long, BundleActivation>();
  }

  public synchronized void open() {
    for (final Bundle bundle : bc.getBundles()) {
      switch (bundle.getState()) {
      case Bundle.RESOLVED:
        scan(bundle);
        break;

      case Bundle.ACTIVE:
        scan(bundle);
        activate(bundle);
        break;
      }
    }

    bc.addBundleListener(this);
  }

  public synchronized void close() {
    bc.removeBundleListener(this);

    for (final BundleActivation handler : handlers.values()) {
      handler.deactivate();
    }
    handlers.clear();
  }

  public synchronized void bundleChanged(final BundleEvent event) {
    final Bundle bundle = event.getBundle();

    switch (event.getType()) {
    case BundleEvent.RESOLVED:
      scan(bundle);
      break;

    case BundleEvent.STARTED:
      activate(bundle);
      break;

    case BundleEvent.STOPPED:
      deactivate(bundle);
      break;

    case BundleEvent.UNRESOLVED:
      clean(bundle);
      break;
    }
  }

  @SuppressWarnings("unchecked")
  private void scan(final Bundle bundle) {
    final String header = (String) bundle.getHeaders().get(Constants.BUNDLE_MODULE);
    if (header == null) {
      return;
    }

    final Class<? extends Module> moduleClass =
        (Class<? extends Module>) Bundles.loadClass(bundle, header);

    final BundleActivation handler = new BundleActivation(bundle, moduleClass);
    handlers.put(bundle.getBundleId(), handler);
  }

  private void activate(final Bundle bundle) {
    final BundleActivation handler = handlers.get(bundle.getBundleId());
    if (handler != null) {
      handler.activate();
    }
  }

  private void deactivate(final Bundle bundle) {
    final BundleActivation handler = handlers.get(bundle.getBundleId());
    if (handler != null) {
      handler.deactivate();
    }
  }

  private void clean(final Bundle bundle) {
    handlers.remove(bundle.getBundleId());
  }
}