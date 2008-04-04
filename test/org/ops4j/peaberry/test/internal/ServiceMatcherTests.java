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

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ops4j.peaberry.internal.ServiceMatcher.findMetaAnnotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.AnnotatedElement;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Service;
import org.testng.annotations.Test;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ServiceMatcherTests")
public final class ServiceMatcherTests {

  @Retention(RUNTIME)
  @interface MetaAnnotation {}

  @Service("meta")
  @Retention(RUNTIME)
  @interface MetaService {}

  @Leased(seconds = Leased.FOREVER)
  @Retention(RUNTIME)
  @interface MetaLeased {}

  Object a;

  @Service("b")
  Object b;

  @Leased(seconds = 1)
  Object c;

  @MetaAnnotation
  Object d;

  @MetaService
  Object e;

  @MetaLeased
  Object f;

  @MetaService
  @Service("g")
  Object g;

  @MetaLeased
  @Leased(seconds = 2)
  Object h;

  AnnotatedElement getElement(String name) {
    try {
      return getClass().getDeclaredField(name);
    } catch (Exception e) {
      assert false : "Missing field " + name;
      return null;
    }
  }

  void checkNone(String name, Class<? extends Annotation> clazz) {
    Annotation a = findMetaAnnotation(getElement(name), clazz);
    assert null == a : "Expected no annotation, got " + a;
  }

  void checkService(String name, String filter) {
    String result = findMetaAnnotation(getElement(name), Service.class).value();
    assert filter.equals(result) : "Expected " + filter + ", got " + result;
  }

  void checkLeased(String name, int seconds) {
    int result = findMetaAnnotation(getElement(name), Leased.class).seconds();
    assert seconds == result : "Expected " + seconds + ", got " + result;
  }

  public void missingAnnotations() {
    checkNone("a", Service.class);
    checkNone("a", Leased.class);
    checkNone("b", Leased.class);
    checkNone("c", Service.class);
    checkNone("d", Service.class);
    checkNone("d", Leased.class);
    checkNone("e", Leased.class);
    checkNone("f", Service.class);
    checkNone("g", Leased.class);
    checkNone("h", Service.class);
  }

  public void serviceSpec() {
    checkService("b", "b");
    checkService("e", "meta");
    checkService("g", "g");
  }

  public void leasedSpec() {
    checkLeased("c", 1);
    checkLeased("f", Leased.FOREVER);
    checkLeased("h", 2);
  }
}
