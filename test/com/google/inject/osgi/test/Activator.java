/**
 * Copyright (C) 2007 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject.osgi.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Activator implements BundleActivator {

  public void start(final BundleContext bc) throws Exception {

    // FIXME: need handshake
    new Thread(new Runnable() {
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {}
        try {
          bc.getBundle(0).stop();
        } catch (BundleException e) {}
      }
    }).start();
  }

  public void stop(BundleContext bc) throws Exception {
  }
}

