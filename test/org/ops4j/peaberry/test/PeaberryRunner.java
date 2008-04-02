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

import org.junit.internal.TextListener;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.JUnitCore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class PeaberryRunner
    extends JUnit4ClassRunner {

  static volatile Injector injector;

  public static void run(Module... modules) {

    injector = Guice.createInjector(modules);

    JUnitCore junit = new JUnitCore();
    junit.addListener(new TextListener());
    for (Module module : modules) {
      if (!module.getClass().isAnonymousClass()) {
        junit.run(module.getClass());
      }
    }
  }

  public PeaberryRunner(Class<?> clazz)
      throws InitializationError {
    super(clazz);
  }

  @Override
  protected Object createTest()
      throws Exception {

    return injector.getInstance(getTestClass().getJavaClass());
  }
}
