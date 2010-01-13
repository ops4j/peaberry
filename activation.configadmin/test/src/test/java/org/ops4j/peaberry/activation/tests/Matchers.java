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
package org.ops4j.peaberry.activation.tests;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.google.inject.matcher.Matchers.*;

import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;

public final class Matchers {
  private Matchers() {
    /* Static utility */
  }
  
  public static Matcher<? super Class<?>> trackedClass(Class<?> type) {
    return subclassesOf(type);
  }
  
  public static Matcher<Method> method(final String name, final Class<?>... args) {
    return new AbstractMatcher<Method>() {
      public boolean matches(Method m) {
        return name.equals(m.getName()) && Arrays.equals(args, m.getParameterTypes());
      }
    };
  }
  
  public static String packageOf(Class<?> c) {
    return c.getPackage().getName();
  }
}
