package org.ops4j.peaberry.activation.invocations.internal;

import static org.ops4j.peaberry.activation.invocations.InvocationListener.*;

import java.lang.reflect.Method;
import java.util.Map;

import org.ops4j.peaberry.activation.invocations.InvocationListener;
import org.ops4j.peaberry.util.AbstractDecorator;

import com.google.inject.matcher.Matcher;

public class FilteredInvocationListenerDecorator 
  extends AbstractDecorator<InvocationListener> {
  
  @Override
  @SuppressWarnings("unchecked")
  protected InvocationListener decorate(final InvocationListener delegate,
      final Map<String, ?> attrs) {
    
    return new FilteredInvocationListener(
         delegate, 
        (Matcher<? super Class<?>>) attrs.get(PROP_MATCHER_TYPE), 
        (Matcher<? super Method>) attrs.get(PROP_MATCHER_METHOD));
  }
}