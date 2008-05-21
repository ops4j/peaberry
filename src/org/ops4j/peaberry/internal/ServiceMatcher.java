/**
 * Copyright (C) 2008 Stuart McCulloch
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

package org.ops4j.peaberry.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import org.ops4j.peaberry.Service;

import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;

/**
 * Provide matchers and other utility methods for {@link Service} annotations.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceMatcher {

  // utility: instances not allowed
  private ServiceMatcher() {}

  /**
   * Matches annotated elements with a {@link Service} annotation, or elements
   * whose annotations have a {@link Service} meta-annotation attached to them.
   * 
   * @return matcher that matches elements with {@link Service} annotations
   */
  public static Matcher<AnnotatedElement> annotatedWithService() {
    return new AbstractMatcher<AnnotatedElement>() {
      public boolean matches(AnnotatedElement element) {
        return findMetaAnnotation(element, Service.class) != null;
      }

      @Override
      public String toString() {
        return "annotatedWithService()";
      }
    };
  }

  /**
   * Search for any annotations of the given type attached to this element, or
   * that are attached to any annotations or meta-annotations on the element.
   * 
   * @param element annotated element
   * @param annotationType annotated type to search for
   * 
   * @return attached annotation, or null if none exists
   */
  public static <T extends Annotation> T findMetaAnnotation(
      AnnotatedElement element, final Class<? extends T> annotationType) {

    if (null == element) {
      return null;
    }

    // keep track of candidates to avoid cycles between meta-annotations
    List<AnnotatedElement> candidates = new ArrayList<AnnotatedElement>();

    candidates.add(element);

    int i = 0;
    while (i < candidates.size()) {
      for (Annotation a : candidates.get(i++).getAnnotations()) {
        final Class<?> aType = a.annotationType();

        if (annotationType.equals(aType)) {
          return annotationType.cast(a);
        } else if (!candidates.contains(aType)) {
          candidates.add(aType);
        }
      }
    }

    return null;
  }
}
