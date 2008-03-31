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

package org.ops4j.peaberry;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.LinkedList;
import java.util.Queue;

import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceMatcher {

  public static Matcher<AnnotatedElement> annotatedWithService() {
    return new AbstractMatcher<AnnotatedElement>() {
      public boolean matches(AnnotatedElement element) {
        return findMetaAnnotation(element, Service.class) != null;
      }

      public String toString() {
        return "annotatedWithService()";
      }
    };
  }

  public static Service getServiceSpec(AnnotatedElement element) {
    return findMetaAnnotation(element, Service.class);
  }

  public static Leased getLease(AnnotatedElement element) {
    return findMetaAnnotation(element, Leased.class);
  }

  private static <T extends Annotation> T findMetaAnnotation(
      AnnotatedElement element, final Class<? extends T> annotationType) {

    Queue<AnnotatedElement> candidates = new LinkedList<AnnotatedElement>();

    candidates.add(element);

    while (false == candidates.isEmpty()) {
      for (Annotation a : candidates.remove().getAnnotations()) {
        if (annotationType.isInstance(a)) {
          return annotationType.cast(a);
        }
        candidates.add(a.annotationType());
      }
    }

    return null;
  }
}
