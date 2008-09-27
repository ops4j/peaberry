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
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class Setting<T> {

  private final Key<? extends T> key;
  private final T instance;

  public Setting(final Key<? extends T> key) {
    this.key = key;
    instance = null;
  }

  public Setting(final T instance) {
    key = null;
    this.instance = instance;
  }

  public T get(final Injector injector) {
    if (key != null && injector != null) {
      return injector.getInstance(key);
    }
    return instance;
  }

  private static final Setting<?> NULL_SETTING = new Setting<Object>(null);

  @SuppressWarnings("unchecked")
  public static <S> Setting<S> nullSetting() {
    return (Setting<S>) NULL_SETTING;
  }
}
