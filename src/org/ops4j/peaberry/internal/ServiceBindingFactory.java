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

import static org.ops4j.peaberry.ServiceMatcher.getServiceSpec;
import static org.ops4j.peaberry.ServiceMatcher.isLeasedService;
import static org.ops4j.peaberry.ServiceMatcher.isStaticService;
import static org.ops4j.peaberry.internal.ServiceProviderFactory.getServiceProvider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.Static;

import com.google.inject.BindingFactory;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.spi.Dependency;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceBindingFactory
    implements BindingFactory<Object> {

  final ServiceRegistry serviceRegistry;

  public ServiceBindingFactory(ServiceRegistry serviceRegistry) {
    this.serviceRegistry = serviceRegistry;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public boolean bind(Dependency dependency, LinkedBindingBuilder lbb) {

    Type memberType = dependency.getKey().getTypeLiteral().getType();

    Member member = dependency.getMember();
    int i = dependency.getParameterIndex();
    AnnotatedElement element = null;

    if (i < 0) {
      element = (AnnotatedElement) member;
    } else if (member instanceof Constructor) {
      element = ((Constructor<?>) member).getParameterTypes()[i];
    } else if (member instanceof Method) {
      element = ((Method) member).getParameterTypes()[i];
    }

    ScopedBindingBuilder sbb =
        lbb.toProvider(getServiceProvider(serviceRegistry, memberType,
            getServiceSpec(element)));

    if (isStaticService(element)) {
      sbb.in(Static.class);
    } else if (isLeasedService(element)) {
      sbb.in(Leased.class);
    }

    return true;
  }
}
