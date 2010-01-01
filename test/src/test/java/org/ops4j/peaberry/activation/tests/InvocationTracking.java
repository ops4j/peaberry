package org.ops4j.peaberry.activation.tests;

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

public abstract class InvocationTracking {
  @Inject
  private BundleContext bc;
  private InvocationTracker tracker;
  
  @Before
  public void findInvocationTracker() {
    this.tracker = getService(InvocationTracker.class);
  }
  
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
  
  protected List<Bundle> listBundles() {
    return Arrays.asList(bc.getBundles());
  }
  
  @SuppressWarnings("unchecked")
  protected <T> T getService(Class<T> type) {
    ServiceReference ref = getReference(type); 
    assertNotNull("Can't obtain service reference " + type.getName(), ref);
    
    T serv = (T) bc.getService(ref);
    assertNotNull("Can't obtain service " + type.getName(), serv);
    
    return serv;
  }
  
  protected ServiceReference getReference(Class<?> type) {
    return bc.getServiceReference(type.getName());
  }
  
  protected ServiceReference[] getReferenceList(Class<?> type) {
    try {
      return bc.getServiceReferences(type.getName(), null);
    } catch (InvalidSyntaxException e) {
      throw new RuntimeException("Unexpected", e);
    }
  }
  
  protected void waitInvocation(final Matcher<? super Class<?>> type,
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
  
  protected void assertNotInvoked(Matcher<? super Class<?>> type, Matcher<? super Method> method) {
    final List<MethodInvocation> invList = tracker.get(type, method);
    assertEquals(0, invList.size());
  }
  
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
  
  protected void resetInvocations() {
    tracker.reset();
  }
}
