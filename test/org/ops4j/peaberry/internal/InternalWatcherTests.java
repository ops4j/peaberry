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

package org.ops4j.peaberry.internal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.util.AbstractDecorator;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.ops4j.peaberry.util.StaticImport;
import org.testng.annotations.Test;

/**
 * Corner-case tests for internal {@link ServiceWatcher} implementations.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class InternalWatcherTests {

  public void testDecoratedWatcherEquality() {

    final ImportDecorator<Object> decoA = new AbstractDecorator<Object>() {
      @Override
      protected Object decorate(Object instance, Map<String, ?> attributes) {
        return instance;
      }
    };

    final ServiceWatcher<Object> watcherA = new AbstractWatcher<Object>() {};

    final ImportDecorator<Object> decoB = new AbstractDecorator<Object>() {
      @Override
      protected Object decorate(Object instance, Map<String, ?> attributes) {
        return instance;
      }
    };

    final ServiceWatcher<Object> watcherB = new AbstractWatcher<Object>() {};

    ServiceWatcher<?> decoratedWatcherA1 = new DecoratedServiceWatcher<Object>(decoA, watcherA);
    ServiceWatcher<?> decoratedWatcherA2 = new DecoratedServiceWatcher<Object>(decoA, watcherA);
    ServiceWatcher<?> decoratedWatcherB1 = new DecoratedServiceWatcher<Object>(decoA, watcherB);
    ServiceWatcher<?> decoratedWatcherB2 = new DecoratedServiceWatcher<Object>(decoB, watcherA);

    assertEquals(decoratedWatcherA1, decoratedWatcherA2);
    assertNotSame(decoratedWatcherB1, decoratedWatcherB2);
    assertNotSame(decoratedWatcherA1, decoratedWatcherB1);
    assertNotSame(decoratedWatcherA2, decoratedWatcherB2);

    assertEquals(decoratedWatcherA1.hashCode(), decoratedWatcherA2.hashCode());
  }

  public void testConcurrentWatcherEquality() {

    final List<Import<Object>> servicesA = new ArrayList<Import<Object>>();
    final List<Import<Object>> servicesB = new ArrayList<Import<Object>>();

    final List<Object> results = new ArrayList<Object>();

    servicesA.add(new StaticImport<Object>("A"));
    servicesB.add(new StaticImport<Object>("B"));

    final ServiceWatcher<Object> watcherA = new AbstractWatcher<Object>() {
      @Override
      protected Object adding(Import<Object> service) {
        final Object instance = service.get();
        results.add(instance);
        return instance;
      }

      @Override
      protected void removed(Object instance) {
        results.remove(instance);
      }
    };

    final ServiceWatcher<Object> watcherB = new AbstractWatcher<Object>() {};

    ServiceWatcher<Object> concurrentWatcherA1 =
        new ConcurrentServiceWatcher<Object>(servicesA, watcherA);
    ServiceWatcher<Object> concurrentWatcherA2 =
        new ConcurrentServiceWatcher<Object>(servicesA, watcherA);
    ServiceWatcher<Object> concurrentWatcherB1 =
        new ConcurrentServiceWatcher<Object>(servicesA, watcherB);
    ServiceWatcher<Object> concurrentWatcherB2 =
        new ConcurrentServiceWatcher<Object>(servicesB, watcherA);

    assertEquals(concurrentWatcherA1, concurrentWatcherA2);
    assertNotSame(concurrentWatcherB1, concurrentWatcherB2);
    assertNotSame(concurrentWatcherA1, concurrentWatcherB1);
    assertNotSame(concurrentWatcherA2, concurrentWatcherB2);

    assertEquals(concurrentWatcherA1.hashCode(), concurrentWatcherA2.hashCode());

    assertEquals(results.toString(), "[]");
    final Export<Object> export = concurrentWatcherA1.add(servicesA.get(0));
    assertEquals(results.toString(), "[A]");

    export.put("C");
    export.attributes(null);

    assertEquals(results.toString(), "[C]");
  }
}
