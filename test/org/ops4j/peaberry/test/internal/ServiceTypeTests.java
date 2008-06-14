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

import static org.ops4j.peaberry.internal.ServiceTypes.expectsSequence;
import static org.ops4j.peaberry.internal.ServiceTypes.getServiceClass;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Test runtime interpretation of service types.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@SuppressWarnings("serial")
@Test(testName = "ServiceTypeTests", suiteName = "Internal")
public final class ServiceTypeTests {

  private void checkType(final Class<?> clazz, final Type type) {
    final Class<?> result = getServiceClass(type);
    assert clazz.equals(result) : "Expected " + clazz + ", got " + result;
  }

  public void serviceTypes() {
    checkType(String.class, new TypeLiteral<String>() {}.getType());
    checkType(Integer.class, new TypeLiteral<Iterable<Integer>>() {}.getType());
    checkType(Map.class, new TypeLiteral<Map<String, Integer>>() {}.getType());
    checkType(Set.class, new TypeLiteral<Iterable<Set<Short>>>() {}.getType());
  }

  private void unary(final Type memberType, final Class<?> serviceType) {
    final boolean unary = !expectsSequence(memberType);
    assert unary : "Expected " + memberType + " to be unary";

    final Class<?> type = getServiceClass(memberType);
    assert serviceType.equals(type) : "Expected " + serviceType + " got " + type;
  }

  private void multi(final Type memberType, final Class<?> serviceType) {
    final boolean multi = expectsSequence(memberType);
    assert multi : "Expected " + memberType + " to be multi";

    final Class<?> type = getServiceClass(memberType);
    assert serviceType.equals(type) : "Expected " + serviceType + " got " + type;
  }

  @SuppressWarnings("unchecked")
  public void sequenceCheck() {

    unary(new TypeLiteral<String>() {}.getType(), String.class);
    unary(new TypeLiteral<Map<String, Iterable<?>>>() {}.getType(), Map.class);
    unary(new TypeLiteral<List<Iterable<Byte>>>() {}.getType(), List.class);

    multi(new TypeLiteral<Iterable>() {}.getType(), Object.class);
    multi(new TypeLiteral<Iterable<?>>() {}.getType(), Object.class);
    multi(new TypeLiteral<Iterable<? super List>>() {}.getType(), Object.class);
    multi(new TypeLiteral<Iterable<? extends String>>() {}.getType(), String.class);

    multi(new TypeLiteral<Iterable<String>>() {}.getType(), String.class);
    multi(new TypeLiteral<Iterable<List<Iterable>>>() {}.getType(), List.class);
    multi(new TypeLiteral<Iterable<Map<String, Iterable>>>() {}.getType(), Map.class);
  }
}
