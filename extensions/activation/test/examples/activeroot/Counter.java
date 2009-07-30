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

package examples.activeroot;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class Counter {
  @Start
  public void start() {
    starts++;
  }

  @Stop
  public void stop() {
    stops++;
  }

  private static int starts;
  private static int stops;

  public synchronized static int starts() {
    return starts;
  }
  
  public synchronized static int stops() {
    return stops;
  }
  
  public synchronized static void reset() {
    starts = 0;
    stops = 0;
  }
}
