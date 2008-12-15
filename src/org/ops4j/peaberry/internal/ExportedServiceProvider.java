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

import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.builders.ExportProvider;
import org.ops4j.peaberry.builders.ImportDecorator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Exported service provider, also provides direct exported services.
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

    final T instance = settings.instance(injector);
    final Map<String, ?> attributes = settings.attributes(injector);

    // create pseudo-import for this service instance
    Import<T> service = new Import<T>() {

      public T get() {
        return instance;
      }

      public Map<String, ?> attributes() {
        return attributes;
      }

      public void unget() {}
    };

    // apply decoration to the service instance to be exported
    final ImportDecorator<? super T> decorator = settings.decorator(injector);
    if (null != decorator) {
      service = decorator.decorate(service);
    }

    return settings.watcher(injector).add(service);
  }

  public Provider<T> direct() {
    final ExportProvider<T> exportProvider = this;
    return new Provider<T>() {
      public T get() {
        // export and get service instance
        return exportProvider.get().get();
      }
    };
  }
}
