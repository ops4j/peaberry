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

package org.ops4j.peaberry.test.internal;

import static org.ops4j.peaberry.Peaberry.leased;
import static org.ops4j.peaberry.Peaberry.service;

import java.lang.annotation.Annotation;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Service;
import org.testng.annotations.Test;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "AnnotationTests", suiteName = "Internal")
public final class AnnotationTests {

  @Service
  public Object plain;

  private interface A {}

  private interface B {}

  @Service(value = "lang=fr", interfaces = {
      A.class, B.class
  })
  @Leased(seconds = 42)
  public Object detailed;

  private void checkAnnotation(String field, Annotation annotation) {
    Annotation a;

    try {
      a = getClass().getField(field).getAnnotation(annotation.annotationType());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    assert annotation.hashCode() == a.hashCode() : "Expected "
        + annotation.hashCode() + ", got " + a.hashCode();

    assert annotation.equals(a);
    assert a.equals(annotation);
  }

  public void checkServiceAnnotation() {

    checkAnnotation("plain", service(null));
    checkAnnotation("plain", service(""));
    checkAnnotation("plain", service("", new Class<?>[] {}));

    checkAnnotation("detailed", service("lang=fr", A.class, B.class));

    assert !service(null).equals(new Object());
  }

  public void checkLeasedAnnotation() {

    checkAnnotation("detailed", leased(42));

    assert !leased(0).equals(new Object());
  }
}
