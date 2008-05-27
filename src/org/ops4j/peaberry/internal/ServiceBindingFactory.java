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

import static org.ops4j.peaberry.internal.ServiceMatcher.findMetaAnnotation;
import static org.ops4j.peaberry.internal.ServiceProviderFactory.getServiceProvider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Service;
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
  private final ServiceRegistry serviceRegistry;

  public ServiceBindingFactory(ServiceRegistry serviceRegistry) {
    this.logger = Logger.getLogger(getClass().getName());
    this.serviceRegistry = serviceRegistry;
  }

  /**
   * {@inheritDoc}
   */
  public <T> boolean bind(Dependency<T> dependency, LinkedBindingBuilder<T> lbb) {

    Member member = dependency.getMember();
    int i = dependency.getParameterIndex();
    AnnotatedElement element = null;

    if (i < 0) {
      element = (AnnotatedElement) member;
    } else if (member instanceof Constructor) {
      element = ((Constructor<?>) member).getParameterTypes()[i];
    } else /* must be 'setter' method */{
      element = ((Method) member).getParameterTypes()[i];
    }

    Key<T> key = dependency.getKey();

    Service spec = findMetaAnnotation(element, Service.class);
    Leased leased = findMetaAnnotation(element, Leased.class);

    Provider<T> serviceProvider =
        getServiceProvider(serviceRegistry, key, spec, leased);

    logger.fine(key + " ==> " + serviceProvider);

    lbb.toProvider(serviceProvider);

    return true;
  }
}
