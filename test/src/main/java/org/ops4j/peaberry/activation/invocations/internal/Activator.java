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

import static org.ops4j.peaberry.Peaberry.*;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class Activator implements BundleActivator {
  @Inject
  private Export<InvocationTrackerImpl> trackerExport;
  
  public void start(final BundleContext bc) {
    Guice.createInjector(osgiModule(bc), new InvocationsModule()).injectMembers(this);
  }

  public void stop(final BundleContext bc) {
    trackerExport.unput();
  }
}
