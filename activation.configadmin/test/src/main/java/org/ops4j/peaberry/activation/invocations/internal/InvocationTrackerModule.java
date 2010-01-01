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

import static com.google.inject.matcher.Matchers.any;

import java.lang.reflect.Method;

import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.activation.invocations.InvocationTracker;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matcher;

/**
 * A module that can be mixed into an {@link com.google.inject.Injector} to
 * capture certain method invocations and store them in an {@link InvocationTracker}
 * service.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 */
public final class InvocationTrackerModule
    extends AbstractModule {
  
  private final Matcher<? super Class<?>> types;
  private final Matcher<? super Method> methods;

  public InvocationTrackerModule(Matcher<? super Class<?>> types) {
    this(types, any());
  }

  public InvocationTrackerModule(Matcher<? super Class<?>> types, Matcher<? super Method> methods) {
    this.types = types;
    this.methods = methods;
  }

  @Override
  protected void configure() {
    final LoggingInterceptor log = new LoggingInterceptor();
    bindInterceptor(types, methods, log);
    requestInjection(log);

    bind(InvocationTracker.class).toProvider(Peaberry.service(InvocationTracker.class).single());
  }
}
