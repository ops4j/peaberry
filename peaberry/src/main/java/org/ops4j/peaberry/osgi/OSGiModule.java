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

import org.ops4j.peaberry.BundleScoped;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.cache.CachingServiceRegistry;
import org.ops4j.peaberry.cache.Chain;
import org.ops4j.peaberry.cache.RegistryChain;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

/**
 * OSGi specific Guice binding {@link Module}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class OSGiModule
    extends AbstractModule {

  private final BundleContext bundleContext;
  private final ServiceRegistry[] registries;

  public OSGiModule(final BundleContext bundleContext, final ServiceRegistry... registries) {
    this.bundleContext = bundleContext;
    this.registries = registries;
  }

  /**
   * Use this constructor if BundleContext is already bound in another module.
   */
  public OSGiModule() {
    this(null);
  }

  @Override
  protected void configure() {
    if (bundleContext != null) {
      bind(BundleContext.class).toInstance(bundleContext);
    }

    bindScope(BundleScoped.class, new BundleScopeImpl(getProvider(BundleContext.class)));

    if (registries.length == 0) {
      bind(ServiceRegistry.class).to(CachingServiceRegistry.class);
    } else {
      bind(ServiceRegistry.class).annotatedWith(Chain.class).to(CachingServiceRegistry.class);
      bind(ServiceRegistry[].class).annotatedWith(Chain.class).toInstance(registries);
      bind(ServiceRegistry.class).to(RegistryChain.class);
    }

    // the binding key class will be used as the bundle-scoped service API
    bind(CachingServiceRegistry.class).to(OSGiServiceRegistry.class).in(BundleScoped.class);
  }
}
