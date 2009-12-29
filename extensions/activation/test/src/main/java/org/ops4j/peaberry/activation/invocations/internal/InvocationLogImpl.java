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

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.ops4j.peaberry.activation.invocations.InvocationLog;

import com.google.inject.matcher.Matcher;


/**
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 */
public class InvocationLogImpl
    implements InvocationLog {

  private final List<MethodInvocation> cache = new LinkedList<MethodInvocation>();

  public synchronized void log(final MethodInvocation inv) {
    cache.add(inv);
  }

  public synchronized List<MethodInvocation> get(final Matcher<? super Class<?>> type,
      final Matcher<? super Method> method) {
    
    List<MethodInvocation> res = new LinkedList<MethodInvocation>();
    for (MethodInvocation inv : cache) {
      if (type.matches(inv.getThis().getClass()) && method.matches(inv.getMethod())) {
        res.add(inv);
      }
    }
    return res;
  }

  public synchronized void reset() {
    cache.clear();
  }
}
