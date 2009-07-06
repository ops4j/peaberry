/**
 * Copyright (C) 2009 Todor Boev
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

package examples.activation.importer.internal;

/**
 * A concurrently running loop that can be shutdown gracefully. Like the
 * underlying thread it can be run once.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 */
public class Loop {
  private final Runnable body;

  private Thread thread;
  private boolean running;

  public Loop(Runnable body) {
    this.body = body;
  }

  public synchronized void start() {
    if (thread != null) {
      throw new IllegalStateException("Loop can run only once");
    }

    thread = new Thread() {
      @SuppressWarnings("synthetic-access")
      @Override
      public void run() {
        while (running) {
          body.run();
        }
      }
    };
    running = true;
    thread.start();
  }

  public synchronized void stop() {
    running = false;
    thread.interrupt();
  }
}
