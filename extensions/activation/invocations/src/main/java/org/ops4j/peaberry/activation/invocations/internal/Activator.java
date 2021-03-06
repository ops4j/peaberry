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
import static org.ops4j.peaberry.util.TypeLiterals.*;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.activation.invocations.InvocationListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class Activator implements BundleActivator {
  @SuppressWarnings("unused")
  @Inject
  private Export<InvocationTrackerImpl> trackerExport;

  public void start(final BundleContext bc) {
    Guice.createInjector(osgiModule(bc), new AbstractModule() {
      @Override
      protected void configure() {
        bind(export(InvocationTrackerImpl.class)).toProvider(
            service(InvocationTrackerImpl.class).export());

        bind(iterable(InvocationListener.class)).toProvider(
            service(InvocationListener.class)
            .decoratedWith(new FilteredInvocationListenerDecorator())
            .multiple());
      }
    }).injectMembers(this);
  }

  public void stop(final BundleContext bc) {
  }
}
