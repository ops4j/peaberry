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

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.builders.ExportProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Exported handle and direct service {@link Provider}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExportedServiceProvider<T>
    implements ExportProvider<T> {

  @Inject
  Injector injector;

  private final ServiceSettings<T> settings;

  public ExportedServiceProvider(final ServiceSettings<T> settings) {
    // clone current state of settings
    this.settings = settings.clone();
  }

  public Export<T> get() {
    return settings.getExport(injector);
  }

  private static final class DirectProvider<T>
      implements Provider<T> {

    @Inject
    Injector injector;

    private final ServiceSettings<T> settings;

    public DirectProvider(final ServiceSettings<T> settings) {
      // settings already cloned
      this.settings = settings;
    }

    public T get() {
      return settings.getExport(injector).get();
    }
  }

  public Provider<T> direct() {
    return new DirectProvider<T>(settings);
  }
}
