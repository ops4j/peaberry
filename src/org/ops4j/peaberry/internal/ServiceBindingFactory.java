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

package org.ops4j.peaberry.internal;

import static org.ops4j.peaberry.internal.ServiceProviderFactory.getServiceProvider;

import java.util.logging.Logger;

import org.ops4j.peaberry.ServiceRegistry;

import com.google.inject.BindingFactory;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.spi.Dependency;

/**
 * {@link BindingFactory} that provides on-demand bindings to dynamic services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceBindingFactory
    implements BindingFactory<Object> {

  /**
   * Use standard logging API.
   */
  private final Logger logger;

  /**
   * Underlying {@link ServiceRegistry} that provides dynamic services.
   */
  private final ServiceRegistry registry;

  public ServiceBindingFactory(final ServiceRegistry serviceRegistry) {
    this.logger = Logger.getLogger(getClass().getName());
    this.registry = serviceRegistry;
  }

  /**
   * {@inheritDoc}
   */
  public <T> boolean bind(final Dependency<T> dependency,
      final LinkedBindingBuilder<T> lbb) {

    final Key<T> key = dependency.getKey();
    final Provider<T> provider = getServiceProvider(registry, key);
    logger.fine(key + " ==> " + provider);
    lbb.toProvider(provider);

    return true;
  }
}
