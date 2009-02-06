/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.eclipse.riena;

import org.ops4j.peaberry.ServiceException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class Activator
    implements BundleActivator {

  public void start(final BundleContext context) {}

  public void stop(final BundleContext context) {}

  public static Activator getDefault() {
    return new Activator();
  }

  public Logger getLogger(@SuppressWarnings("unused") final Class<?> clazz) {
    return new Logger() {
      public void log(final int level, final String message) {
        throw new ServiceException("Injection error: " + message);
      }
    };
  }
}
