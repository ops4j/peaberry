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

package org.ops4j.peaberry;

/**
 * A service registry is a {@link ServiceScope} that allows lookup of services.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface ServiceRegistry
    extends ServiceScope<Object> {

  /**
   * Lookup services from the registry, constrained by the given filter.
   * 
   * @param clazz expected service interface
   * @param filter service attribute filter
   * @return ordered sequence of imported services, most recommended first
   */
  <T> Iterable<Import<T>> lookup(Class<T> clazz, AttributeFilter filter);

  /**
   * Watch for services in the registry, constrained by the given filter.
   * 
   * @param clazz expected service interface
   * @param filter service attribute filter
   * @param scope the watching scope which receives any matching services
   */
  <T> void watch(Class<T> clazz, AttributeFilter filter, ServiceScope<? super T> scope);
}
