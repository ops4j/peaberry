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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.activation.invocations.InvocationTracker;

import com.google.inject.Inject;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 */
public class LoggingInterceptor
    implements MethodInterceptor {

  private InvocationTracker log;

  @Inject
  public void init(final InvocationTracker l) {
    log = l;
  }

  public Object invoke(final MethodInvocation inv)
      throws Throwable {
    
    log(inv);
    
    /*
     * Log service is optional. It is definitely a bad practice to silently
     * introduce a required service to the classes we enhance with this logging
     * aspect.
     */
    try {
      log.log(inv);
    } catch (ServiceUnavailableException sue) {
      error(inv);
    }

    return inv.proceed();
  }

  private void log(final MethodInvocation inv) {
    System.out.println(format("Logged", inv));
  }

  private void error(final MethodInvocation inv) {
    System.out.println(format("*** Dropped ***", inv));
  }
  
  private static String format(final String tag, final MethodInvocation inv) {
    final StringBuilder res = new StringBuilder();

    res.append("[Invocation " + tag + "] ");
    res.append(inv.getThis().getClass().getName().split("\\$\\$")[0]);
    res.append("#");
    res.append(inv.getMethod().getName());

    res.append("(");
    final Object[] args = inv.getArguments();
    for (int i = 0; i < args.length; i++) {
      res.append(args[i]);
      if (i < args.length - 1) {
        res.append(", ");
      }
    }
    res.append(")");

    return res.toString();
  }
}
