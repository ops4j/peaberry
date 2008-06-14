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

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.builders.DynamicServiceBuilder;
import org.ops4j.peaberry.builders.FilteredServiceBuilder;
import org.ops4j.peaberry.builders.ScopedServiceBuilder;
import org.ops4j.peaberry.builders.ServiceProxyBuilder;

import com.google.inject.Provider;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ServiceBuilderImpl<T>
    implements DynamicServiceBuilder<T> {

  public ServiceBuilderImpl(final Class<? extends T> target) {
  // TODO Auto-generated constructor stub
  }

  public FilteredServiceBuilder<T> leased(final int seconds) {
    // TODO Auto-generated method stub
    return this;
  }

  public FilteredServiceBuilder<T> constant() {
    // TODO Auto-generated method stub
    return this;
  }

  public ScopedServiceBuilder<T> filter(final String filter) {
    // TODO Auto-generated method stub
    return this;
  }

  public ServiceProxyBuilder<T> registry(final ServiceRegistry registry) {
    // TODO Auto-generated method stub
    return this;
  }

  public Provider<T> single() {
    // TODO Auto-generated method stub
    return null;
  }

  public Provider<Iterable<T>> multiple() {
    // TODO Auto-generated method stub
    return null;
  }
}
