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

import static java.util.Collections.unmodifiableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExtensionImport
    implements Import<Object>, Comparable<ExtensionImport> {

  private static final int INVALID = -1;
  private static final int DORMANT = 0;
  private static final int ACTIVE = 1;

  private final long id;
  private final IConfigurationElement config;
  private final Class<?> clazz;

  private Object instance;
  private volatile int state;

  private final Map<String, ?> attributes;
  private final List<Export<?>> watchers;

  public ExtensionImport(final long id, final IConfigurationElement config, final Class<?> clazz) {

    this.id = id;
    this.config = config;
    this.clazz = clazz;

    attributes = collectAttributes(config);
    watchers = new ArrayList<Export<?>>(2);
  }

  public boolean matches(final AttributeFilter filter) {
    return filter.matches(attributes);
  }

  public Object get() {
    if (DORMANT == state) {
      synchronized (this) {
        if (DORMANT == state) {
          try {
            instance = ExtensionBeanFactory.newInstance(clazz, config);
          } catch (final RuntimeException re) {
            throw new ServiceUnavailableException(re);
          } finally {
            state = ACTIVE;
          }
        }
      }
    }
    return instance;
  }

  public Map<String, ?> attributes() {
    return config.isValid() ? attributes : null;
  }

  public void unget() {/* do nothing */}

  /**
   * Protected from concurrent access by {@link ExtensionListener}.
   */
  public void addWatcher(final Export<?> export) {
    watchers.add(export);
  }

  /**
   * Protected from concurrent access by {@link ExtensionListener}.
   */
  public void invalidate() {
    notifyWatchers();
    watchers.clear();
    instance = null;
    state = INVALID; // force memory flush
  }

  private void notifyWatchers() {
    for (final Export<?> export : watchers) {
      try {
        export.unput();
      } catch (final RuntimeException re) {/* ignore */} // NOPMD
    }
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof ExtensionImport) {
      // allocated id is a unique identifier
      return id == ((ExtensionImport) rhs).id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ id >>> 32);
  }

  public int compareTo(final ExtensionImport rhs) {

    if (id == rhs.id) {
      return 0;
    }

    // prefer lower allocated id
    return id < rhs.id ? -1 : 1;
  }

  private static Map<String, Object> collectAttributes(final IConfigurationElement config) {
    final Map<String, Object> map = new HashMap<String, Object>();

    try {
      final IExtension extension = config.getDeclaringExtension();

      map.put("@id", extension.getUniqueIdentifier());
      map.put("@label", extension.getLabel());
      map.put("@contributor", config.getContributor());
      map.put("@point", extension.getExtensionPointUniqueIdentifier());

      map.put("name()", config.getName());
      map.put("text()", config.getValue());

      for (final String key : config.getAttributeNames()) {
        map.put(key, config.getAttribute(key));
      }

    } catch (final InvalidRegistryObjectException re) {
      map.clear(); // invalid, so wipe slate clean
    }

    return unmodifiableMap(map);
  }
}
