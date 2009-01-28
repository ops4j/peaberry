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

package org.ops4j.peaberry.internal;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * Immutable setting that accepts either a binding key or explicit instance.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@SuppressWarnings("PMD.AbstractNaming")
abstract class Setting<T> {

  /**
   * @param injector optional injector
   * @return injected setting value
   */
  public abstract T get(final Injector injector);

  /**
   * @return setting based on explicit instance
   */
  public static <T> Setting<T> newSetting(final T instance) {
    if (null == instance) {
      // null instances are not tolerated
      throw new IllegalArgumentException("null instance");
    }

    return new Setting<T>() {
      private boolean configured;

      @Override
      public T get(final Injector injector) {
        if (!configured && null != injector) {
          // given value may need injecting
          injector.injectMembers(instance);
          configured = true;
        }
        return instance;
      }
    };
  }

  /**
   * @return setting based on binding key
   */
  public static <T> Setting<T> newSetting(final Key<? extends T> key) {
    if (null == key) {
      // null binding keys are not tolerated
      throw new IllegalArgumentException("null binding key");
    }

    return new Setting<T>() {
      @Override
      public T get(final Injector injector) {
        // query the injector for the value
        return injector.getInstance(key);
      }
    };
  }

  @SuppressWarnings("unchecked")
  public static <T> Setting<T> nullSetting() {
    return (Setting<T>) NULL_SETTING;
  }

  // constant null setting, safe to share between builders
  private static final Setting<Object> NULL_SETTING = new Setting<Object>() {
    @Override
    public Object get(final Injector injector) {
      return null;
    }
  };
}
