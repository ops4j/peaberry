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

package org.ops4j.peaberry.tests;

import static java.util.Collections.singletonMap;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.osgi.framework.Constants.SERVICE_RANKING;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.testng.annotations.Test;

import com.google.inject.Inject;

/**
 * Test service contention and ranking consistency.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(sequential = true)
public final class ServiceContentionTests
    extends InjectableTestCase {

  @Override
  protected void configure() {
    bind(export(DummyService.class)).toProvider(service(DummyServiceImpl.class).export());
    bind(DummyService.class).toProvider(service(DummyService.class).single());

    // use null placeholder, real implementation will be exported later on
    bind(export(RankService.class)).toProvider(service((RankService) null).export());
    bind(iterable(RankService.class)).toProvider(service(RankService.class).multiple());
  }

  protected static interface DummyService {
    String test();
  }

  public static class DummyServiceImpl
      implements DummyService {

    public String test() {
      try {
        Thread.sleep(100);
      } catch (final InterruptedException e) {}

      return "DONE";
    }
  }

  @Inject
  Export<DummyService> exportedService;

  @Inject
  DummyService importedService;

  public void testSingleServiceContention() {
    final Thread[] threads = new Thread[42];

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

    exportedService.unput();

    try {
      importedService.test();
      assert false : "No service expected";
    } catch (final ServiceUnavailableException e) {}
  }

  protected static interface RankService {
    int rank();
  }

  @Inject
  Iterable<RankService> rankings;

  public void testServiceRankingIntegrity() {
    final Thread[] threads = new Thread[42];

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Runnable() {
        public void run() {

          // line all threads up at this point
          synchronized (ServiceContentionTests.this) {
            try {
              ServiceContentionTests.this.wait();
            } catch (final InterruptedException e) {}
          }

          @SuppressWarnings("unchecked")
          final Export<RankService> exportedRank = (Export) getInstance(export(RankService.class));

          // export random rank for random length of time
          final int rank = (int) (1000 * Math.random());
          exportedRank.attributes(singletonMap(SERVICE_RANKING, rank));
          exportedRank.put(new RankService() {
            public int rank() {
              return rank;
            }
          });

          try {
            Thread.sleep((int) (10 * Math.random()));
          } catch (final InterruptedException e2) {}

          exportedRank.unput();
        }
      });
    }

    for (final Thread t : threads) {
      t.start();
    }

    final Thread[] testThread = new Thread[1];
    testThread[0] = new Thread(new Runnable() {
      public void run() {

        int prevRank;
        do {
          // release all the ranking threads
          synchronized (ServiceContentionTests.this) {
            ServiceContentionTests.this.notifyAll();
          }

          // continually test ranking order
          prevRank = Integer.MAX_VALUE;
          for (final RankService next : rankings) {
            try {
              int nextRank = next.rank();
              assert prevRank >= nextRank : "Expected " + prevRank + " >= " + nextRank;
              prevRank = nextRank;
            } catch (final ServiceUnavailableException e) {}
          }
        } while (testThread[0] == Thread.currentThread());
      }
    });

    testThread[0].start();

    // wait for everything to finish
    for (final Thread t : threads) {
      try {
        t.join();
      } catch (final InterruptedException e) {}
    }

    testThread[0] = null;
  }
}
