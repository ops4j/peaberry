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
import static java.util.logging.Level.WARNING;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceScope;

/**
 * Keep track of imported Eclipse Extensions that provide a specific interface.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ExtensionListener
    implements IRegistryEventListener {

  private static final Logger LOGGER = Logger.getLogger(ExtensionListener.class.getName());

  private final IExtensionRegistry registry;
  private final Class<?> clazz;

  private final String point;
  private final boolean aggregate;
  private long idCounter;

  private final List<ExtensionImport> imports;
  private final List<ServiceScope<Object>> watchers;

  ExtensionListener(final IExtensionRegistry registry, final Class<?> clazz) {
    final ExtensionInterface metadata = clazz.getAnnotation(ExtensionInterface.class);

    this.registry = registry;
    this.clazz = clazz;

    // use package as extension point id if no annotation
    if (null == metadata || metadata.value().isEmpty()) {
      point = clazz.getPackage().getName();
    } else {
      point = metadata.value();
    }

    // do we need to combine elements into a single bean?
    aggregate = metadata != null && metadata.aggregate();

    imports = new ArrayList<ExtensionImport>(4);
    watchers = new ArrayList<ServiceScope<Object>>(2);
  }

  synchronized void start() {
    final IExtensionPoint[] extensionPoints;

    // register listener first to avoid race condition
    if (Object.class == clazz) {
      registry.addListener(this);
      extensionPoints = registry.getExtensionPoints();
    } else {
      registry.addListener(this, point);
      extensionPoints = new IExtensionPoint[]{registry.getExtensionPoint(point)};
    }

    // safety check in case there was no matching extension point
    if (extensionPoints.length == 0 || null == extensionPoints[0]) {
      return;
    }

    // retrieve any matching extensions for each point
    for (final IExtensionPoint p : extensionPoints) {
      for (final IExtension e : p.getExtensions()) {
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

    // collect extension ids to speed up checking
    final Set<String> ids = new HashSet<String>(extensions.length);
    for (final IExtension e : extensions) {
      ids.add(e.getUniqueIdentifier());
    }

    for (final Iterator<ExtensionImport> i = imports.iterator(); i.hasNext();) {
      final ExtensionImport extensionImport = i.next();

      // remove any elements that belong to the removed extensions
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
  synchronized void addWatcher(final ServiceScope scope) {
    if (!watchers.contains(scope) && watchers.add(scope)) {

      // report existing imports to the new scope
      for (final ExtensionImport i : imports) {
        notifyWatcher(scope, i);
      }
    }
  }

  private void insertExtension(final IExtension extension) {
    final IConfigurationElement[] configurations;
    if (aggregate) {
      configurations = new IConfigurationElement[]{new AggregatedExtension(extension)};
    } else {
      configurations = extension.getConfigurationElements();
    }

    // create an import for each major configuration element
    for (final IConfigurationElement config : configurations) {
      final ExtensionImport i = new ExtensionImport(++idCounter, config, clazz);
      imports.add(i);

      // report the new import to any watching scopes
      for (final ServiceScope<Object> scope : watchers) {
        notifyWatcher(scope, i);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static void notifyWatcher(final ServiceScope scope, final ExtensionImport i) {
    try {
      final Export export = scope.add(i);
      if (null != export) {
        i.addWatcher(export);
      }
    } catch (final RuntimeException re) {
      LOGGER.log(WARNING, "Exception in service watcher", re);
    }
  }

  synchronized ExtensionImport findNextImport(final ExtensionImport prevImport,
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
