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

import org.ops4j.peaberry.Leased;

/**
 * Implementation of the {@link Leased} annotation.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class LeasedAnnotation
    implements Leased {

  private final int seconds;

  public LeasedAnnotation(int seconds) {
    this.seconds = seconds;
  }

  public int seconds() {
    return seconds;
  }

  public Class<? extends Annotation> annotationType() {
    return Leased.class;
  }

  @Override
  public int hashCode() {
    return (127 * "seconds".hashCode()) ^ Integer.valueOf(seconds).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Leased)) {
      return false;
    }

    return seconds == ((Leased) o).seconds();
  }

  @Override
  public String toString() {
    if (seconds < 0) {
      return "@Leased(FOREVER)";
    }

    return String.format("@Leased(%s)", seconds);
  }
}
