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

package org.ops4j.peaberry.eclipse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceScope;

/**
 * Eclipse Extension {@link ServiceRegistry} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class EclipseRegistry
    implements ServiceRegistry {

  private static class SingletonHolder {
    static final ServiceRegistry thisRegistry = new EclipseRegistry();
  }

  // ensure the main Eclipse registry is a singleton
  public static ServiceRegistry eclipseRegistry() {
    return SingletonHolder.thisRegistry;
  }

  private final IExtensionRegistry extensionRegistry;

  // per-class map of extension listeners (much faster than polling)
  private final ConcurrentMap<String, ExtensionListener> listenerMap =
      new ConcurrentHashMap<String, ExtensionListener>();

  EclipseRegistry() {
    extensionRegistry = RegistryFactory.getRegistry();
  }

  public <T> Iterable<Import<T>> lookup(final Class<T> clazz, final AttributeFilter filter) {
    return new IterableExtension<T>(registerListener(clazz), filter);
  }

  public <T> Export<T> add(final Import<T> service) {
    // Extension registry is currently read-only
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings("unchecked")
  public <T> void watch(final Class<T> clazz, final AttributeFilter filter,
      final ServiceScope<? super T> scope) {

    registerListener(clazz).addWatcher(new FilteredExtensionScope(filter, scope));
  }

  @Override
  public String toString() {
    return String.format("EclipseRegistry[%s]", extensionRegistry.toString());
  }

  @Override
  public int hashCode() {
    return extensionRegistry.hashCode();
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof EclipseRegistry) {
      return extensionRegistry.equals(((EclipseRegistry) rhs).extensionRegistry);
    }
    return false;
  }

  private <T> ExtensionListener registerListener(final Class<T> clazz) {
    final Class<?> safeClazz = null == clazz ? Object.class : clazz;
    final String clazzName = safeClazz.getName();
    ExtensionListener listener;

    listener = listenerMap.get(clazzName);
    if (null == listener) {
      final ExtensionListener newListener = new ExtensionListener(extensionRegistry, safeClazz);
      listener = listenerMap.putIfAbsent(clazzName, newListener);
      if (null == listener) {
        newListener.start();
        return newListener;
      }
    }

    return listener;
  }
}
