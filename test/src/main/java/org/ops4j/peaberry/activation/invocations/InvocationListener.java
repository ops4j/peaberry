package org.ops4j.peaberry.activation.invocations;

import org.aopalliance.intercept.MethodInvocation;

public interface InvocationListener {
  String PROP_MATCHER_TYPE = "matcher.method";
  String PROP_MATCHER_METHOD = "matcher.type";
  
  void invoked(MethodInvocation call);
}
