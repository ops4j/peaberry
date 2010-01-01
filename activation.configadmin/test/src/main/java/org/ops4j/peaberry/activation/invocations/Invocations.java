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

