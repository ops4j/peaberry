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

import static java.lang.String.format;

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

  private final String filter;
  private final Class<?>[] interfaces;
  private final Seconds lease;
  private final String name;

  public ServiceAnnotation(String filter, Class<?>[] interfaces,
      int leaseInSeconds, String name) {

    this.filter = filter;
    this.interfaces = interfaces;
    this.lease = new SecondsAnnotation(leaseInSeconds);
    this.name = name;
  }

  public String filter() {
    return filter;
  }

  public Class<?>[] interfaces() {
    return interfaces;
  }

  public Seconds lease() {
    return lease;
  }

  public String name() {
    return name;
  }

  public Class<? extends Annotation> annotationType() {
    return Service.class;
  }

  @Override
  public int hashCode() {
    return ((127 * "filter".hashCode()) ^ Objects.hashCode(filter))
        + ((127 * "interfaces".hashCode()) ^ Arrays.hashCode(interfaces))
        + ((127 * "lease".hashCode()) ^ Objects.hashCode(lease))
        + ((127 * "name".hashCode()) ^ Objects.hashCode(name));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Service)) {
      return false;
    }

    Service other = (Service) o;

    return Objects.equal(filter, other.filter())
        && Arrays.equals(interfaces, other.interfaces())
        && Objects.equal(lease, other.lease())
        && Objects.equal(name, other.name());
  }

  private static final String FORMAT =
      "@" + Service.class.getName()
          + "(filter=%s, interfaces=%s, lease=%s, name=%s)";

  @Override
  public String toString() {
    return format(FORMAT, filter, Arrays.toString(interfaces), lease, name);
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
      return format(SECONDS_FORMAT, value);
    }
  }
}
