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

  private String[] attributes = {};
  private String filter = "";
  private Class<?>[] interfaces = {};
  private int leaseInSeconds = 0;

  /**
   * Start building a new {@link Service} specification.
   * 
   * @return new {@link Service} builder
   */
  public static ServiceBuilder service() {
    return new ServiceBuilder();
  }

  /**
   * @param _attributes sequence of LDAP (RFC-2253) attributes
   * @return current {@link Service} builder
   */
  public ServiceBuilder attributes(final String... _attributes) {
    this.attributes = nonNull(_attributes, "attributes");
    return this;
  }

  /**
   * @param _filter LDAP (RFC-1960) filter
   * @return current {@link Service} builder
   */
  public ServiceBuilder filter(final String _filter) {
    this.filter = nonNull(_filter, "filter");
    return this;
  }

  /**
   * @param _interfaces custom service API
   * @return current {@link Service} builder
   */
  public ServiceBuilder interfaces(final Class<?>... _interfaces) {
    this.interfaces = nonNull(_interfaces, "interfaces");
    return this;
  }

  /**
   * @param seconds service lease period
   * @return current {@link Service} builder
   */
  public ServiceBuilder lease(final int seconds) {
    this.leaseInSeconds = seconds;
    return this;
  }

  /**
   * @return configured {@link Service} instance
   */
  public Service build() {
    return new ServiceAnnotation(attributes, filter, interfaces, leaseInSeconds);
  }
}
