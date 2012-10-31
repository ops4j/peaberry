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
import static java.util.logging.Level.WARNING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * {@link Import} implementation backed by an Eclipse Extension.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ExtensionImport
    implements Import<Object>, Comparable<ExtensionImport> {

  private static final Logger LOGGER = Logger.getLogger(ExtensionImport.class.getName());

  private static final int INVALID = -1;
  private static final int DORMANT = 0;
  private static final int ACTIVE = 1;

  private final long id; // internal sequence number
  private final IConfigurationElement config;
  private final Class<?> clazz;

  private volatile Object instance;
  private volatile int state;

  private volatile Map<String, ?> attributes;
  private final List<Export<?>> watchers;

  ExtensionImport(final long id, final IConfigurationElement config, final Class<?> clazz) {

    this.id = id;
    this.config = config;
    this.clazz = clazz;

    watchers = new ArrayList<Export<?>>(2);
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
    // perform lazy conversion
    if (null == attributes) {
      synchronized (this) {
        if (null == attributes) {
          attributes = collectAttributes(config);
        }
      }
    }
    return attributes;
  }

  public void unget() {/* do nothing */}

  public boolean available() {
    return config.isValid();
  }

  /**
   * Protected from concurrent access by {@link ExtensionListener}.
   */
  void addWatcher(final Export<?> export) {
    watchers.add(export);
  }

  IConfigurationElement getConfigurationElement() {
    return config;
  }

  /**
   * Protected from concurrent access by {@link ExtensionListener}.
   */
  boolean invalidate(final Collection<IExtension> candidates) {
    try {
      if (!candidates.contains(config.getDeclaringExtension())) {
        return false; /* this doesn't belong to any candidate */
      }
    } catch (final InvalidRegistryObjectException e) {/* already invalid */} // NOPMD

    notifyWatchers();
    watchers.clear();
    synchronized (this) {
      instance = null;
      state = INVALID;
    }

    return true; /* clean-up parent list */
  }

  private void notifyWatchers() {
    for (final Export<?> export : watchers) {
      try {
        export.unput();
      } catch (final RuntimeException re) {
        LOGGER.log(WARNING, "Exception in service watcher", re);
      }
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

      // use @ to avoid conflicting with attributes in the XML
      safePut(map, "@id", extension.getUniqueIdentifier());
      safePut(map, "@label", extension.getLabel());
      safePut(map, "@contributor", config.getContributor());
      safePut(map, "@namespace", config.getNamespaceIdentifier());
      safePut(map, "@point", extension.getExtensionPointUniqueIdentifier());

      // similarly use () to avoid conflicts
      safePut(map, "name()", config.getName());
      safePut(map, "text()", config.getValue());

      // now load the actual attributes from the XML
      for (final String key : config.getAttributeNames()) {
        safePut(map, key, config.getAttribute(key));
      }

    } catch (final InvalidRegistryObjectException re) {
      map.clear(); // invalid, so wipe slate clean
    }

    return unmodifiableMap(map);
  }

  private static <T> void safePut(final Map<String, T> map, final String key, final T value) {
    if (null != value) {
      map.put(key, value);
    }
  }
}
