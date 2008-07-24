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

import org.testng.internal.ObjectFactoryImpl;

import com.google.inject.Injector;

/**
 * Construct testcase objects using Guice.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class GuiceObjectFactory
    extends ObjectFactoryImpl {

  private static final long serialVersionUID = 1L;
  private static volatile Injector injector;

  public static void setInjector(final Injector injector) {
    GuiceObjectFactory.injector = injector;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object newInstance(final Constructor ctor, final Object... initargs) {
    if (injector != null) {
      return injector.getInstance(ctor.getDeclaringClass());
    }
    return super.newInstance(ctor, initargs);
  }
}
