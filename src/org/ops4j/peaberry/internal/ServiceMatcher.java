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

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Mandatory;
import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.Static;

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

  public static Leased getLeasedSpec(AnnotatedElement element) {
    return findMetaAnnotation(element, Leased.class);
  }

  public static boolean isStaticService(AnnotatedElement element) {
    return findMetaAnnotation(element, Static.class) != null;
  }

  public static boolean isLeasedService(AnnotatedElement element) {
    return findMetaAnnotation(element, Leased.class) != null;
  }

  public static boolean isMandatoryService(AnnotatedElement element) {
    return findMetaAnnotation(element, Mandatory.class) != null;
  }

  private static <T extends Annotation> T findMetaAnnotation(
      AnnotatedElement element, final Class<? extends T> annotationType) {

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
