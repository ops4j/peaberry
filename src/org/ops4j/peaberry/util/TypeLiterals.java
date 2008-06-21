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

package org.ops4j.peaberry.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.ops4j.peaberry.Export;

import com.google.inject.TypeLiteral;

/**
 * Collection of utility methods for dealing with generic TypeLiterals.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class TypeLiterals {

  // instances not allowed
  private TypeLiterals() {}

  /**
   * Create a TypeLiteral representing <code>Iterable&lt;T&gt;</code>.
   * 
   * @param clazz service interface
   * @return Iterable<T> type
   */
  @SuppressWarnings("unchecked")
  public static <T> TypeLiteral<Iterable<T>> iterable(final Class<T> clazz) {
    return (TypeLiteral<Iterable<T>>) TypeLiteral.get(new ParameterizedType() {

      public Type[] getActualTypeArguments() {
        return new Type[] {clazz};
      }

      public Type getRawType() {
        return Iterable.class;
      }

      public Type getOwnerType() {
        return null;
      }
    });
  }

  /**
   * Create a TypeLiteral representing <code>Export&lt;T&gt;</code>.
   * 
   * @param clazz service interface
   * @return Export<T> type
   */
  @SuppressWarnings("unchecked")
  public static <T> TypeLiteral<Export<? extends T>> export(final Class<T> clazz) {
    return (TypeLiteral<Export<? extends T>>) TypeLiteral.get(new ParameterizedType() {

      public Type[] getActualTypeArguments() {
        return new Type[] {clazz};
      }

      public Type getRawType() {
        return Export.class;
      }

      public Type getOwnerType() {
        return null;
      }
    });
  }
}
