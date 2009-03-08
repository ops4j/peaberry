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

import static java.util.Collections.singletonMap;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.util.AbstractDecorator;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.testng.annotations.Test;

/**
 * Corner-case tests for service watchers.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServiceWatcherTests {

  private static final class TestImport
      implements Import<String> {

    private final String instance;
    private final Map<String, ?> attributes;

    TestImport(final String instance) {
      this.instance = instance;
      attributes = singletonMap("id", instance);
    }

    public String get() {
      return instance;
    }

    public Map<String, ?> attributes() {
      return attributes;
    }

    public void unget() {}
  }

  public void testConcurrentWatcher() {
    final List<Import<String>> services = new ArrayList<Import<String>>();

    final String[] placeholder = new String[2];
    final ServiceWatcher<String> targetWatcher = new AbstractWatcher<String>() {
      @Override
      protected String adding(final Import<String> service) {
        placeholder[0] = service.get();
        placeholder[1] = (String) service.attributes().get("id");
        return super.adding(service);
      }

      @Override
      protected void modified(final String instance, final Map<String, ?> attributes) {
        placeholder[0] = instance;
        placeholder[1] = (String) attributes.get("id");
        super.modified(instance, attributes);
      }
    };

    final ServiceWatcher<String> concurrentWatcher =
        new ConcurrentServiceWatcher<String>(services, targetWatcher);

    assertEquals(concurrentWatcher.hashCode(), targetWatcher.hashCode() ^ services.hashCode());

    final TestImport aImport = new TestImport("A");
    final TestImport bImport = new TestImport("B");

    services.add(aImport);

    final Export<String> aExport = concurrentWatcher.add(aImport);

    // original settings
    assertEquals(aExport.get(), "A");
    assertEquals(aExport.attributes().get("id"), "A");
    aExport.unget();

    // make sure they reached the watcher
    assertEquals(placeholder[0], "A");
    assertEquals(placeholder[1], "A");

    // notify the instance change
    aExport.put("X");

    assertEquals(placeholder[0], "X");
    assertEquals(placeholder[1], "A");

    // notify the attributes change
    aExport.attributes(singletonMap("id", "X"));

    assertEquals(placeholder[0], "X");
    assertEquals(placeholder[1], "X");

    // new best service
    services.add(0, bImport);
    concurrentWatcher.add(bImport);

    // make sure it reached the watcher
    assertEquals(placeholder[0], "B");
    assertEquals(placeholder[1], "B");

    aExport.put("Y");
    aExport.attributes(singletonMap("id", "Y"));

    // change to A should be ignored
    assertEquals(placeholder[0], "B");
    assertEquals(placeholder[1], "B");
  }

  public void testDecoratedWatcher() {
    final ImportDecorator<String> decorator = new AbstractDecorator<String>() {
      @Override
      protected String decorate(final String instance, final Map<String, ?> attributes) {
        return "{" + instance + "}";
      }
    };

    final String[] placeholder = new String[2];
    final ServiceWatcher<String> targetWatcher = new AbstractWatcher<String>() {
      @Override
      protected String adding(final Import<String> service) {
        placeholder[0] = service.get();
        placeholder[1] = (String) service.attributes().get("id");
        return super.adding(service);
      }

      @Override
      protected void modified(final String instance, final Map<String, ?> attributes) {
        placeholder[0] = instance;
        placeholder[1] = (String) attributes.get("id");
        super.modified(instance, attributes);
      }
    };

    final ServiceWatcher<String> decoratedWatcher =
        new DecoratedServiceWatcher<String>(decorator, targetWatcher);

    assertEquals(decoratedWatcher.hashCode(), targetWatcher.hashCode() ^ decorator.hashCode());

    final TestImport aImport = new TestImport("A");
    final Export<String> aExport = decoratedWatcher.add(aImport);

    // original settings
    assertEquals(aExport.get(), "{A}");
    assertEquals(aExport.attributes().get("id"), "A");
    aExport.unget();

    // make sure they reached the watcher
    assertEquals(placeholder[0], "{A}");
    assertEquals(placeholder[1], "A");

    // notify the instance change
    aExport.put("X");

    assertEquals(placeholder[0], "{X}");
    assertEquals(placeholder[1], "A");

    // notify the attributes change
    aExport.attributes(singletonMap("id", "X"));

    assertEquals(placeholder[0], "{X}");
    assertEquals(placeholder[1], "X");
  }
}
