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

import java.util.Arrays;

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
    if (null == bundleContext) {
      throw new IllegalArgumentException("null bundle context");
    }

    this.bundleContext = bundleContext;
    this.registries = registries;
  }

  @Override
  protected void configure() {
    bind(BundleContext.class).toInstance(bundleContext);
    bindScope(BundleScoped.class, new BundleScopeImpl(bundleContext));

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

  @Override
  public String toString() {
    return String.format("OSGiModule[%s, %s]", bundleContext.getBundle(), Arrays
        .toString(registries));
  }

  @Override
  public int hashCode() {
    return bundleContext.hashCode() ^ Arrays.hashCode(registries);
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof OSGiModule) {
      return bundleContext.equals(((OSGiModule) rhs).bundleContext)
          && Arrays.equals(registries, ((OSGiModule) rhs).registries);
    }
    return false;
  }
}
