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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Factory methods for direct static services.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class DirectServiceFactory {

  // instances not allowed
  private DirectServiceFactory() {}

  public static <S, T extends S> Iterable<T> directServices(final Iterable<Import<T>> handles,
      final ImportDecorator<S> decorator) {

    final Collection<T> services = new ArrayList<T>();
    final Iterator<Import<T>> i = handles.iterator();

    while (i.hasNext()) {
      final T instance = nextService(i, decorator);
      if (instance != null) {
        services.add(instance);
      }
    }

    return services;
  }

  public static <S, T extends S> T directService(final Iterable<Import<T>> handles,
      final ImportDecorator<S> decorator) {

    return nextService(handles.iterator(), decorator);
  }

  private static <S, T extends S> T nextService(final Iterator<Import<T>> i,
      final ImportDecorator<S> decorator) {

    try {
      return (null == decorator ? i.next() : decorator.decorate(i.next())).get();
    } catch (final Exception e) {
      return null;
    }
  }
}
