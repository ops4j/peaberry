/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.eclipse;

import static org.eclipse.core.runtime.ContributorFactoryOSGi.resolve;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.eclipse.EclipseRegistry.eclipseRegistry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.ServiceRegistry;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;

/**
 * Module extension that binds the contributing {@link BundleContext} as well as
 * a {@link ServiceRegistry} that monitors OSGi services and Eclipse extensions.
 * 
 * @see GuiceExtensionFactory
 * @see Peaberry#osgiModule(BundleContext, ServiceRegistry...)
 * @see EclipseRegistry
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class PeaberryExtension
    extends AbstractModule
    implements IExecutableExtension {

  private BundleContext bundleContext;

  public void setInitializationData(final IConfigurationElement config, final String name,
      final Object data) {
    bundleContext = resolve(config.getContributor()).getBundleContext();
  }

  @Override
  protected final void configure() {
    install(osgiModule(bundleContext, eclipseRegistry()));
  }
}
