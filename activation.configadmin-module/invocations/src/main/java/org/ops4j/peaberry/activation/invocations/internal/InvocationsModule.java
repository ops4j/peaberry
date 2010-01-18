/**
 * Copyright (C) 2010 Todor Boev
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

import org.ops4j.peaberry.activation.invocations.InvocationListener;

import com.google.inject.AbstractModule;

public class InvocationsModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(export(InvocationTrackerImpl.class)).toProvider(
        service(InvocationTrackerImpl.class).export());
    
    bind(iterable(InvocationListener.class)).toProvider(
        service(InvocationListener.class)
        .decoratedWith(new FilteredInvocationListenerDecorator())
        .multiple());
  }
}