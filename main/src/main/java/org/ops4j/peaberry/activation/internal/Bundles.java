/**
 * Copyright (C) 2009 Todor Boev
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

package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.PeaberryActivationException;
import org.osgi.framework.Bundle;

/**
 * @author Todor Boev (rinsvind@gmail.com)
 * 
 */
public class Bundles {
  private Bundles() {}

  public static String toString(final Bundle bundle) {
    return bundle.getSymbolicName() + "(" + bundle.getBundleId() + ")";
  }

  public static Class<?> loadClass(final Bundle bundle, final String name) {
    try {
      return bundle.loadClass(name);
    } catch (final ClassNotFoundException e) {
      throw new PeaberryActivationException("Failed to load " + name + " from " + toString(bundle));
    }
  }
}
