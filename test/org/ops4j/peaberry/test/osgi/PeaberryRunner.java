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

import java.lang.reflect.Constructor;

import org.testng.IObjectFactory;
import org.testng.TestNG;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class PeaberryRunner
    implements IObjectFactory {

  private static final long serialVersionUID = 1L;
  private static volatile Injector injector;
  private static int iteration = 0;

  public static void run(final Module... modules) {

    iteration++;

    Thread testThread = new Thread(new Runnable() {
      public void run() {

        injector = Guice.createInjector(modules);

        Class<?>[] testClasses = new Class<?>[modules.length];
        for (int i = 0; i < testClasses.length; i++) {
          testClasses[i] = modules[i].getClass();
        }

        TestNG testNG = new TestNG();

        testNG.setTestClasses(testClasses);
        testNG.setObjectFactory(PeaberryRunner.class);

        testNG.setOutputDirectory("reports/testNG/test_" + iteration);
        testNG.setDefaultSuiteName(testClasses[0].getSimpleName());
        testNG.setDefaultTestName("peaberry");

        testNG.run();
      }
    });

    try {
      testThread.start();
      testThread.join();
    } catch (InterruptedException e) {}
  }

  @SuppressWarnings("unchecked")
  public Object newInstance(Constructor constructor, Object... args) {
    return injector.getInstance(constructor.getDeclaringClass());
  }
}
