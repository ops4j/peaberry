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

package org.ops4j.peaberry.test;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.google.inject.Module;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class TestActivator
    implements BundleActivator {

  static BundleContext bundleContext;

  public void start(final BundleContext bundleContext)
      throws Exception {

    TestActivator.bundleContext = bundleContext;

    new Thread(new Runnable() {
      public void run() {
        try {
          Module peaberry = Peaberry.getBundleModule(bundleContext);
          PeaberryRunner.run(new ServiceLeasingTest(), peaberry);
          PeaberryRunner.run(new ManualBindingTest());
        } finally {
          try {
            bundleContext.getBundle(0).stop();
          } catch (BundleException e) {
            // don't mind this...
          }
        }
      }
    }).start();
  }

  public static BundleContext getBundleContext() {
    return bundleContext;
  }

  public void stop(BundleContext bc)
      throws Exception {}
}
