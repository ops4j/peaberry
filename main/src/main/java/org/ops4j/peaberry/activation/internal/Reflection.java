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

package org.ops4j.peaberry.activation.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ops4j.peaberry.activation.PeaberryActivationException;

/**
 * Wraps common reflection operations converting their checked exceptions to
 * unchecked crashes.
 * 
 * @author Todor Boev (rinsvind@gmail.com)
 */
public class Reflection {
  private Reflection() {}

  public static <T> T create(final Class<T> type) {
    try {
      try {
        return type.newInstance();
      } catch (final InstantiationException e) {
        return type.getConstructor().newInstance();
      }
    } catch (final Exception e) {
      throw new PeaberryActivationException("Error creating " + type + " from default constructor", e);
    }
  }

  public static Method findMethod(final Class<?> type, final String name,
      final Class<?>... argTypes) {
    try {
      return type.getMethod(name, argTypes);
    } catch (final SecurityException e) {
      throw new PeaberryActivationException(e);
    } catch (final NoSuchMethodException e) {
      return null;
    }
  }

  public static List<Method> findMethods(final Class<?> type,
      final Class<? extends Annotation> tag, final Class<?>... argTypes) {

    final List<Method> res = new ArrayList<Method>();

    for (final Method meth : type.getMethods()) {
      if (meth.getAnnotation(tag) == null) {
        continue;
      }

      if (findMethod(type, meth.getName(), argTypes) == null) {
        continue;
      }

      res.add(meth);
    }

    return res;
  }

  public static Object invoke(final Object that, final Method method, final Object... args) {
    try {
      return method.invoke(that, args);
    } catch (final Exception e) {
      throw new PeaberryActivationException("Error invoking " + that + "#" + method, e);
    }
  }
}
