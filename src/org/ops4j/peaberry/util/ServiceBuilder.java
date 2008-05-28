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

import static com.google.inject.internal.Objects.nonNull;

import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.internal.ServiceAnnotation;

/**
 * Runtime builder of {@link Service} specifications.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceBuilder {

  private String name = "";
  private String filter = "";
  private Class<?>[] interfaces = {};
  private int leaseInSeconds = 0;

  public static ServiceBuilder service() {
    return new ServiceBuilder();
  }

  public ServiceBuilder name(String _name) {
    this.name = nonNull(_name, "name");
    return this;
  }

  public ServiceBuilder filter(String _filter) {
    this.filter = nonNull(_filter, "filter");
    return this;
  }

  public ServiceBuilder interfaces(Class<?>... _interfaces) {
    this.interfaces = nonNull(_interfaces, "interfaces");
    return this;
  }

  public ServiceBuilder lease(int seconds) {
    this.leaseInSeconds = seconds;
    return this;
  }

  public Service build() {
    return new ServiceAnnotation(filter, interfaces, leaseInSeconds, name);
  }
}
