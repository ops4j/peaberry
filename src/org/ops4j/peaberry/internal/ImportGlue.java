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

import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * Around-advice glue code, specifically optimised for peaberry.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ImportGlue {

  private volatile Object handle;
  private final boolean constant;

  /**
   * @param handle service instance, or an indirect reference to it
   * @param constant when true cache the resolved service instance
   */
  public ImportGlue(final Object handle, final boolean constant) {
    this.handle = handle;
    this.constant = constant;
  }

  /**
   * Optimised glue code, used as a template for proxy methods.
   */
  @SuppressWarnings("null")
  public Object glue() {

    Import<?> h = null;
    Object o = handle;

    // unwind indirect references
    while (o instanceof Import) {
      h = ((Import<?>) o);
      o = h.get();
    }

    // caching for static service imports
    if (constant && handle != o && o != null) {
      synchronized (this) {
        if (handle instanceof Import) {
          handle = o;
        } else {
          o = handle;
        }
      }
    }

    try {
      return o.toString(); // replaced by actual invocation
    } catch (final Exception e) {
      throw new ServiceUnavailableException(e);
    } finally {
      if (null != h) {
        h.unget(); // only need to release the last reference
      }
    }
  }
}
