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

import static java.util.Collections.binarySearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceScope;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class ExtensionListener
    implements IRegistryEventListener {

  private final IExtensionRegistry registry;
  private final Class<?> clazz;

  private final String point;
  private final boolean wrap;

  private final List<ExtensionImport> imports;
  private final List<ServiceScope<Object>> watchers;

  private long idCounter;

  public ExtensionListener(final IExtensionRegistry registry, final Class<?> clazz) {
    final ExtensionInterface metadata = clazz.getAnnotation(ExtensionInterface.class);

    this.registry = registry;
    this.clazz = clazz;

    if (null == metadata || metadata.point().isEmpty()) {
      point = clazz.getPackage().getName();
    } else {
      point = metadata.point();
    }

    wrap = metadata != null && metadata.heterogeneous();

    imports = new ArrayList<ExtensionImport>(4);
    watchers = new ArrayList<ServiceScope<Object>>(2);
  }

  public synchronized void start() {
    // register listener first to avoid race condition
    registry.addListener(this, point);

    // retrieve any matching extensions that are already registered
    final IExtensionPoint extensionPoint = registry.getExtensionPoint(point);
    if (extensionPoint != null) {
      for (final IExtension e : extensionPoint.getExtensions()) {
        insertExtension(e);
      }
    }
  }

  public synchronized void added(final IExtension[] extensions) {
    for (final IExtension e : extensions) {
      insertExtension(e);
    }
  }

  public synchronized void removed(final IExtension[] extensions) {

    final Set<String> ids = new HashSet<String>();
    for (final IExtension e : extensions) {
      ids.add(e.getUniqueIdentifier());
    }

    for (final Iterator<ExtensionImport> i = imports.iterator(); i.hasNext();) {
      final ExtensionImport extensionImport = i.next();

      final Map<String, ?> attributes = extensionImport.attributes();
      if (null == attributes || ids.contains(attributes.get("@id"))) {
        i.remove();

        extensionImport.invalidate();
      }
    }
  }

  public void added(final IExtensionPoint[] points) {/* do nothing */}

  public void removed(final IExtensionPoint[] points) {/* do nothing */}

  @SuppressWarnings("unchecked")
  public synchronized void addWatcher(final ServiceScope scope) {
    if (!watchers.contains(scope) && watchers.add(scope)) {

      // report existing imports to the new scope
      for (final ExtensionImport i : imports) {
        final Export export = scope.add(i);
        if (null != export) {
          i.addWatcher(export);
        }
      }
    }
  }

  private void insertExtension(final IExtension extension) {
    IConfigurationElement[] elements;
    if (wrap) {
      elements = new IConfigurationElement[]{new ConfigurationWrapper(extension)};
    } else {
      elements = extension.getConfigurationElements();
    }

    for (final IConfigurationElement i : elements) {
      imports.add(new ExtensionImport(++idCounter, i, clazz));
    }
  }

  public synchronized ExtensionImport findNextImport(final ExtensionImport prevImport,
      final AttributeFilter filter) {

    if (imports.isEmpty()) {
      return null;
    }

    if (prevImport == null && filter == null) {
      return imports.get(0);
    }

    // estimate last position based on previous value and current list
    return findNextImport(filter, null == prevImport ? ~0 : binarySearch(imports, prevImport));
  }

  private ExtensionImport findNextImport(final AttributeFilter filter, final int prevIndex) {

    // may need to flip position if previous import is no longer in the list
    for (int i = prevIndex < 0 ? ~prevIndex : prevIndex + 1; i < imports.size(); i++) {

      // now do a linear search applying the given filter
      final ExtensionImport nextImport = imports.get(i);
      if (null == filter || nextImport.matches(filter)) {
        return nextImport;
      }
    }

    return null; // no matching extension
  }
}
