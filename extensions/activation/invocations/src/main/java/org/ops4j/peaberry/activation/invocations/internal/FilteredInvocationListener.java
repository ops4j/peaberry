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

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.ops4j.peaberry.activation.invocations.InvocationListener;

import com.google.inject.matcher.Matcher;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class FilteredInvocationListener implements InvocationListener {
  private final InvocationListener delegate;
  private final Matcher<? super Class<?>> type;
  private final Matcher<? super Method> method;
  
  public FilteredInvocationListener(final InvocationListener delegate,
      final Matcher<? super Class<?>> type, final Matcher<? super Method> method) {
    
    this.delegate = delegate;
    this.type = type;
    this.method = method;
  }
  
  public void invoked(MethodInvocation inv) {
    if (type.matches(inv.getThis().getClass()) && method.matches(inv.getMethod())) {
      delegate.invoked(inv);
    }
  }
}
