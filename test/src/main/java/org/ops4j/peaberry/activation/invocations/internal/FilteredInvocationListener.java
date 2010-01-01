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
