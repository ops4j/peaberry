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

import static com.google.inject.internal.Objects.equal;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.ops4j.peaberry.Service;

/**
 * Implementation of the {@link Service} annotation.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceAnnotation
    implements Service {

  private final String value;
  private final Class<?>[] interfaces;

  public ServiceAnnotation(String value, Class<?>... interfaces) {
    this.value = (null == value ? "" : value);
    this.interfaces = interfaces;
  }

  public String value() {
    return value;
  }

  public Class<?>[] interfaces() {
    return interfaces;
  }

  public Class<? extends Annotation> annotationType() {
    return Service.class;
  }

  @Override
  public int hashCode() {
    return ((127 * "value".hashCode()) ^ value.hashCode())
        + ((127 * "interfaces".hashCode()) ^ Arrays.hashCode(interfaces));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Service)) {
      return false;
    }

    Service other = (Service) o;

    return equal(value, other.value())
        && Arrays.equals(interfaces, other.interfaces());
  }

  @Override
  public String toString() {
    String api = Arrays.toString(interfaces);
    return String.format("@Service(\"%s\",%s)", value, api);
  }
}
