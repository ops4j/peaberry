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

import static org.ops4j.peaberry.util.ServiceBuilder.service;

import java.lang.annotation.Annotation;

import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.Service.Seconds;
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

  @Service(attributes = {
      "a=X", "b=Y"
  }, filter = "(&(lang=fr)(tz=utc))", interfaces = {
      A.class, B.class
  }, lease = @Seconds(42))
  public Object detailed;

  private void checkAnnotation(String field, Annotation annotation) {
    Annotation a;

    try {
      a = getClass().getField(field).getAnnotation(annotation.annotationType());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    assert annotation.equals(a);
    assert a.equals(annotation);

    assert a.hashCode() == annotation.hashCode() : "Expected " + a.hashCode()
        + ", got " + annotation.hashCode();
  }

  public void checkServiceAnnotation() {

    checkAnnotation("plain", service().build());
    checkAnnotation("plain", service().filter("").build());
    checkAnnotation("plain", service().interfaces(new Class<?>[] {}).build());
    checkAnnotation("plain", service().lease(0).build());

    checkAnnotation("detailed", service().attributes("a=X", "b=Y").filter(
        "(&(lang=fr)(tz=utc))").interfaces(A.class, B.class).lease(42).build());
  }

  public void checkNotEquals() {

    assert !service().build().equals(new Object());
    assert !service().build().lease().equals(new Object());
  }

  public void checkAnnotationTypes() {

    assert service().build().annotationType().equals(Service.class);
    assert service().build().lease().annotationType().equals(Seconds.class);
  }
}
