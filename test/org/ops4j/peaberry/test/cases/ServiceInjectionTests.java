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

package org.ops4j.peaberry.test.cases;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import java.util.Iterator;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import examples.ids.IdService;

/**
 * Test single and multiple service injection, plus iterator flexibility.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceInjectionTests
    extends InjectableTestCase {

  @Inject
  IdService id;

  @Inject
  Iterable<IdService> ids;

  @Override
  protected void configure() {
    bind(IdService.class).toProvider(service(IdService.class).single());
    bind(iterable(IdService.class)).toProvider(service(IdService.class).multiple());
  }

  public void testServiceInjection() {
    reset();

    final Iterator<IdService> i = ids.iterator();

    missing(id);
    check(ids, "[]");
    assertFalse(i.hasNext()); // nothing yet

    register(3, "A");

    check(id, "A");
    check(ids, "[A]");
    assertEquals(i.next(), id); // we have an A

    register(-1, "B");

    check(id, "A");
    check(ids, "[A, B]");
    final IdService b = i.next(); // we have a B
    check(b, "B");

    register(8, "C");

    check(id, "C");
    check(ids, "[C, A, B]");
    assertFalse(i.hasNext()); // C is before B, so won't appear in next()

    unregister("A", "C");

    check(id, "B");
    check(ids, "[B]");
    assertFalse(i.hasNext()); // only B is left
    assertEquals(b, id);

    try {
      i.remove();
      fail("Expected UnsupportedOperationException");
    } catch (final UnsupportedOperationException e) {}

    unregister("B");
    missing(b); // service has gone
  }
}
