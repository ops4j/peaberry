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

package org.ops4j.peaberry.test.osgi;

import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Key;

/**
 * Test service contention.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(testName = "ServiceContentionTests", suiteName = "OSGi")
public final class ServiceContentionTests
    extends OSGiServiceTester {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    binder.bind(export(DummyService.class)).toProvider(
        registration(Key.get(DummyServiceImpl.class)).export());

    binder.bind(DummyService.class).toProvider(service(DummyService.class).single());
  }

  protected static interface DummyService {
    String test();
  }

  static class DummyServiceImpl
      implements DummyService {

    public String test() {
      try {
        Thread.sleep(100);
      } catch (final InterruptedException e) {}

      return "DONE";
    }
  }

  @Inject
  DummyService importedService;

  @Inject
  Export<DummyService> exportedService;

  public void testContention() {

    final Thread[] threads = new Thread[8];

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Runnable() {
        public void run() {
          assert "DONE".equals(importedService.test());
        }
      });
    }

    for (final Thread t : threads) {
      t.start();
    }

    for (final Thread t : threads) {
      try {
        t.join();
      } catch (final InterruptedException e) {}
    }

    assert "DONE".equals(importedService.test());

    exportedService.remove();

    try {
      importedService.test();
      assert false : "No service expected";
    } catch (final ServiceUnavailableException e) {}
  }
}
