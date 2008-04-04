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

package org.ops4j.peaberry.test;

import java.util.Map;

import org.testng.TestNG;
import org.testng.TestNGCommandLineArgs;
import org.testng.TestNGException;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class Runner
    extends TestNG {

  public Runner() {
    System.out.println("peaberry test harness");
    System.out.println("=====================");
  }

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Map params = checkConditions(TestNGCommandLineArgs.parseCommandLine(args));

    TestNG testNG = new Runner();
    testNG.configure(params);

    try {
      testNG.run();
    } catch (TestNGException e) {
      e.printStackTrace();
    }
  }
}
