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

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.ops4j.peaberry.Service;

import com.google.inject.internal.Objects;

/**
 * Implementation of the {@link Service} annotation.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceAnnotation
    implements Service {

  private final String[] attributes;
  private final String filter;
  private final Class<?>[] interfaces;
  private final Seconds lease;

  public ServiceAnnotation(String[] attributes, String filter,
      Class<?>[] interfaces, int leaseInSeconds) {

    this.attributes = attributes.clone();
    this.filter = filter;
    this.interfaces = interfaces.clone();
    this.lease = new SecondsAnnotation(leaseInSeconds);
  }

  public String[] attributes() {
    return attributes.clone();
  }

  public String filter() {
    return filter;
  }

  public Class<?>[] interfaces() {
    return interfaces.clone();
  }

  public Seconds lease() {
    return lease;
  }

  public Class<? extends Annotation> annotationType() {
    return Service.class;
  }

  @Override
  public int hashCode() {
    return ((127 * "attributes".hashCode()) ^ Arrays.hashCode(attributes))
        + ((127 * "filter".hashCode()) ^ Objects.hashCode(filter))
        + ((127 * "interfaces".hashCode()) ^ Arrays.hashCode(interfaces))
        + ((127 * "lease".hashCode()) ^ Objects.hashCode(lease));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Service)) {
      return false;
    }

    Service other = (Service) o;

    return Arrays.equals(attributes, other.attributes())
        && Objects.equal(filter, other.filter())
        && Arrays.equals(interfaces, other.interfaces())
        && Objects.equal(lease, other.lease());
  }

  private static final String SERVICE_FORMAT =
      "@" + Service.class.getName()
          + "(attributes=%s, filter=%s, interfaces=%s, lease=%s)";

  @Override
  public String toString() {
    return String.format(SERVICE_FORMAT, Arrays.toString(attributes), filter,
        Arrays.toString(interfaces), lease);
  }

  /**
   * Implementation of the {@link Seconds} annotation.
   */
  private static final class SecondsAnnotation
      implements Seconds {

    private final int value;

    public SecondsAnnotation(int value) {
      this.value = value;
    }

    public int value() {
      return value;
    }

    public Class<? extends Annotation> annotationType() {
      return Seconds.class;
    }

    @Override
    public int hashCode() {
      return ((127 * "value".hashCode()) ^ Integer.valueOf(value).hashCode());
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Seconds)) {
        return false;
      }

      Seconds other = (Seconds) o;

      return value == other.value();
    }

    private static final String SECONDS_FORMAT =
        "@" + Seconds.class.getName() + "(value=%d)";

    @Override
    public String toString() {
      return String.format(SECONDS_FORMAT, value);
    }
  }
}
