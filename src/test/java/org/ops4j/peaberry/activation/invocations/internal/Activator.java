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

package org.ops4j.peaberry.activation.invocations.internal;

import org.ops4j.peaberry.activation.invocations.InvocationLog;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 *
 */
public class Activator implements BundleActivator {
  public void start(final BundleContext bc) {
    bc.registerService(InvocationLog.class.getName(), new InvocationLogImpl(), null);
  }

  public void stop(final BundleContext bc) {
    /* Nothing to do */
  }
}
