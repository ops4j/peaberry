/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.tests;

import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.matcher.Matchers.identicalTo;
import static com.google.inject.matcher.Matchers.not;
import static com.google.inject.matcher.Matchers.returns;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Decorators.chain;
import static org.ops4j.peaberry.util.Decorators.intercept;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.util.AbstractDecorator;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import examples.ids.Id;

/**
 * Test service interception.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceInterceptionTests
    extends InjectableTestCase {

  @Inject
  Id id;

  @Override
  protected void configure() {
    bind(Id.class).toProvider(service(Id.class).decoratedWith(chain(new AbstractDecorator<Id>() {
      @Override
      protected Id decorate(final Id instance, final Map<String, ?> attributes) {
        assertEquals(attributes.get("id"), "A");
        return instance;
      }
    }, intercept(any(), not(returns(identicalTo(int.class))), new MethodInterceptor() {
      public Object invoke(final MethodInvocation mi)
          throws Throwable {

        assertEquals(mi.getStaticPart(), mi.getMethod());
        assertTrue(mi.getThis().getClass().toString().endsWith("IdImpl"));

        if (mi.getMethod().getName().equals("toString")) {
          assertEquals(mi.getArguments(), null);
        } else if (mi.getMethod().getName().equals("wait")) {
          assertEquals(mi.getArguments().length, 1);
          assertEquals(mi.getArguments()[0], 8);
        }

        return mi.proceed();
      }
    }, new MethodInterceptor() {
      public Object invoke(final MethodInvocation mi)
          throws Throwable {
        if (mi.getMethod().getReturnType() == String.class) {
          return mi.getMethod() + " = " + mi.proceed();
        }
        return mi.proceed();
      }
    }))).single());
  }

  public void testServiceInterception() {
    reset();

    register("A");

    check(id, "public java.lang.String java.lang.Object.toString() = A");

    try {
      synchronized (id) {
        id.wait(8);
      }
    } catch (final InterruptedException e) {}

    id.hashCode();
  }

  public void testMissingInterceptor() {
    try {
      intercept(any(), any());
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {}
  }

  public void testNullInterception() {
    final String[] placeholder = new String[1];

    final Import<CharSequence> service = intercept(any(), any(), new MethodInterceptor() {
      public Object invoke(final MethodInvocation mi)
          throws Throwable {
        return mi.proceed();
      }
    }).decorate(new Import<CharSequence>() {

      public CharSequence get() {
        return placeholder[0];
      }

      public Map<String, ?> attributes() {
        return null;
      }

      public void unget() {}
    });

    assertNull(service.get());
    placeholder[0] = "intercept";
    assertEquals(service.get().toString(), "intercept");
    placeholder[0] = null;

    try {
      service.get().toString();
      fail("Expected ServiceUnavailableException");
    } catch (final ServiceUnavailableException e) {}
  }
}
