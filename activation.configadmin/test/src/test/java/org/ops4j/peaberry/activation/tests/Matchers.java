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
