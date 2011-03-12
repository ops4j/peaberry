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
package org.ops4j.peaberry.activation.invocations.util;

import static junit.framework.Assert.*;
import static org.ops4j.peaberry.activation.invocations.InvocationListener.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Callable;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.ops4j.pax.exam.Inject;
import org.ops4j.peaberry.activation.invocations.InvocationListener;
import org.ops4j.peaberry.activation.invocations.InvocationTracker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.matcher.Matcher;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public abstract class InvocationTracking {
  @Inject
  private BundleContext bc;
  private InvocationTracker tracker;
  
  /**
   * 
   */
  @Before
  public void findInvocationTracker() {
    this.tracker = getService(InvocationTracker.class);
  }
  
  /**
   * @param symbolicName
   * @return
   */
  protected Bundle getBundle(String symbolicName) {
    Bundle res = null;
    for (Bundle b : listBundles()) {
      if (b.getSymbolicName().equals(symbolicName)) {
        res = b;
        break;
      }
    }
    
    assertNotNull("Can't find bundle " + symbolicName, res);
    return res;
  }
  
  /**
   * @return
   */
  protected List<Bundle> listBundles() {
    return Arrays.asList(bc.getBundles());
  }
  
  /**
   * @param <T>
   * @param type
   * @return
   */
  @SuppressWarnings("unchecked")
  protected <T> T getService(Class<T> type) {
    ServiceReference ref = getReference(type); 
    assertNotNull("Can't obtain service reference " + type.getName(), ref);
    
    T serv = (T) bc.getService(ref);
    assertNotNull("Can't obtain service " + type.getName(), serv);
    
    return serv;
  }
  
  /**
   * @param type
   * @return
   */
  protected ServiceReference getReference(Class<?> type) {
    return bc.getServiceReference(type.getName());
  }
  
  /**
   * @param type
   * @return
   */
  protected ServiceReference[] getReferenceList(Class<?> type) {
    try {
      return bc.getServiceReferences(type.getName(), null);
    } catch (InvalidSyntaxException e) {
      throw new RuntimeException("Unexpected", e);
    }
  }
  
  /**
   * @param type
   * @param method
   */
  protected void assertNotInvoked(Matcher<? super Class<?>> type, Matcher<? super Method> method) {
    final List<MethodInvocation> invList = tracker.get(type, method);
    assertEquals(0, invList.size());
  }
  
  /**
   * @param type
   * @param method
   * @param expArgs
   */
  protected void assertInvoked(Matcher<? super Class<?>> type, Matcher<? super Method> method, Object... expArgs) {
    final List<MethodInvocation> invList = tracker.get(type, method);
    assertEquals(1, invList.size());

    final MethodInvocation inv = invList.get(0);
    final Object[] actArgs = inv.getArguments();
    
    assertEquals(expArgs.length, actArgs.length);
    
    for (int i = 0; i < expArgs.length; i++) {
      assertEquals(expArgs[i], actArgs[i]);
    }
  }
  
  /**
   * 
   */
  protected void resetInvocations() {
    tracker.reset();
  }
  
  /**
   * 
   */
  public interface InvocationHookMatcherBuilder {
    InvocationHookTimeoutBuilder andWait(Matcher<? super Class<?>> type, 
        Matcher<? super Method> method);
  }
  
  /**
   * 
   */
  public interface InvocationHookTimeoutBuilder {
    void until(long timeout) throws Exception;
  }
  
  /**
   * 
   */
  private class InvocationHookBuilder 
    implements InvocationHookMatcherBuilder, InvocationHookTimeoutBuilder {
    
    private Callable<Void> code;
    private Matcher<? super Class<?>> type;
    private Matcher<? super Method> method;
    
    public InvocationHookBuilder(Callable<Void> code) {
      this.code = code;
    }
    
    public InvocationHookTimeoutBuilder andWait(Matcher<? super Class<?>> type,
        Matcher<? super Method> method) {
      this.type = type;
      this.method = method;
      return this;
    }

    public void until(long timeout) throws Exception {
      waitInvocation(type, method, timeout, code);
    }
  }
  
  /**
   * @param code
   * @return
   */
  protected InvocationHookMatcherBuilder call(Callable<Void> code) {
    return new InvocationHookBuilder(code);
  }
  
  /**
   * @param type
   * @param method
   * @param timeout
   * @param code
   * @throws Exception
   */
  private void waitInvocation(final Matcher<? super Class<?>> type,
      final Matcher<? super Method> method, final long timeout, final Callable<Void> code)
      throws Exception {
    
    final Boolean[] signal = { false };
    
    /* Setup the waiter */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable();
    props.put(PROP_MATCHER_TYPE, type);
    props.put(PROP_MATCHER_METHOD, method);
    
    final ServiceRegistration reg = bc.registerService(
      InvocationListener.class.getName(),
      new InvocationListener() {
        public void invoked(MethodInvocation call) {
          synchronized (signal) {
            if (signal[0]) {
              throw new RuntimeException("Only one invocation expected");
            }
            
            signal[0] = true;
            signal.notifyAll();
          }
        }
      },
      props);
    
    try {
      /* Perform the operation */
      code.call();
      
      /* Wait until the effect takes place */
      synchronized (signal) {
        while (!signal[0]) {
          long time = System.currentTimeMillis();
          
          try {
            signal.wait(timeout);
          } catch (InterruptedException e) {
          }
          
          if (System.currentTimeMillis() - time > timeout) {
            fail("Invocation timeout " + timeout + ": " + type + "#" + method);
          }
        }
      }
    } finally {
      reg.unregister();
    }
  }
}
