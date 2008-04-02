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

import java.lang.reflect.Constructor;

import org.testng.IObjectFactory;
import org.testng.TestNG;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class PeaberryRunner
    implements IObjectFactory {

  private static final long serialVersionUID = 1L;
  private static volatile Injector injector;

  public static void run(Module... modules) {

    injector = Guice.createInjector(modules);

    Class<?>[] testClasses = new Class<?>[modules.length];
    for (int i = 0; i < testClasses.length; i++) {
      testClasses[i] = modules[i].getClass();
    }

    TestNG testNG = new TestNG();
    testNG.setDefaultTestName("OSGi");
    testNG.setDefaultSuiteName("peaberry");
    testNG.setOutputDirectory("reports/testNG");
    testNG.setObjectFactory(PeaberryRunner.class);
    testNG.setTestClasses(testClasses);
    testNG.run();
  }

  @SuppressWarnings("unchecked")
  public Object newInstance(Constructor constructor, Object... args) {
    return injector.getInstance(constructor.getDeclaringClass());
  }
}
