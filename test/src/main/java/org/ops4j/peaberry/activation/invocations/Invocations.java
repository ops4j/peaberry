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
package org.ops4j.peaberry.activation.invocations;

import static com.google.inject.matcher.Matchers.*;

import java.lang.reflect.Method;

import org.ops4j.peaberry.activation.invocations.internal.InvocationTrackerModule;

import com.google.inject.Module;
import com.google.inject.matcher.Matcher;

public final class Invocations {
  private Invocations() {
    /* Static utility */
  }
  
  public static Module trackerModule(Matcher<? super Class<?>> types) {
    return new InvocationTrackerModule(types, any());
  }
  
  public static Module trackerModule(Matcher<? super Class<?>> types, Matcher<? super Method> methods) {
    return new InvocationTrackerModule(types, methods);
  }
}

