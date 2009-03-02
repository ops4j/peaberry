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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.builders.ImportDecorator;

/**
 * Factory methods for direct (also known as static) services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class DirectServiceFactory {

  // instances not allowed
  private DirectServiceFactory() {}

  static <S, T extends S> Iterable<T> directServices(final Iterable<Import<T>> services,
      final ImportDecorator<S> decorator) {

    final List<T> instances = new ArrayList<T>();
    final Iterator<Import<T>> i = services.iterator();

    while (i.hasNext()) {
      // collect all valid services into snapshot list
      final T instance = nextService(i, decorator);
      if (null != instance) {
        instances.add(instance);
      }
    }

    return unmodifiableList(instances);
  }

  static <S, T extends S> T directService(final Iterable<Import<T>> services,
      final ImportDecorator<S> decorator) {

    final Iterator<Import<T>> i = services.iterator();

    while (i.hasNext()) {
      // return the first valid service found
      final T instance = nextService(i, decorator);
      if (null != instance) {
        return instance;
      }
    }
    return null;
  }

  private static <S, T extends S> T nextService(final Iterator<Import<T>> i,
      final ImportDecorator<S> decorator) {

    try {
      return (null == decorator ? i.next() : decorator.decorate(i.next())).get();
    } catch (final ServiceUnavailableException e) {/* default to null */} // NOPMD

    return null;
  }
}
