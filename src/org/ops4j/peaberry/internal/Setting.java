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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * Immutable setting that accepts either a binding key or explicit instance.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
abstract class Setting<T> {

  public abstract T get(final Injector injector);

  public abstract Type getRawType();

  public static <T> Setting<T> newSetting(final T instance) {
    if (null == instance) {
      throw new IllegalArgumentException();
    }

    return new Setting<T>() {

      @Override
      public T get(final Injector injector) {
        return instance;
      }

      @Override
      public Type getRawType() {
        return instance.getClass();
      }
    };
  }

  public static <T> Setting<T> newSetting(final Key<? extends T> key) {
    if (null == key) {
      throw new IllegalArgumentException();
    }

    return new Setting<T>() {

      @Override
      public T get(final Injector injector) {
        return injector.getInstance(key);
      }

      @Override
      public Type getRawType() {
        Type type = key.getTypeLiteral().getType();
        if (type instanceof ParameterizedType) {
          type = ((ParameterizedType) type).getRawType();
        }
        if (type instanceof Class) {
          return type;
        }
        return Object.class;
      }
    };
  }

  @SuppressWarnings("unchecked")
  public static <S> Setting<S> nullSetting() {
    return (Setting<S>) NULL_SETTING;
  }

  private static final Setting<Object> NULL_SETTING = new Setting<Object>() {

    @Override
    public Object get(final Injector injector) {
      return null;
    }

    @Override
    public Type getRawType() {
      return Object.class;
    }
  };
}
