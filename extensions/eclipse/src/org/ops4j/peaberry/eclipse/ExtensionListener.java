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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceScope;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class ExtensionListener
    implements IRegistryEventListener {

  private final List<IConfigurationElement> elements;

  public ExtensionListener(Class<?> clazz) {
    elements = new ArrayList<IConfigurationElement>();
  }

  public void start() {}

  public void added(IExtension[] extensions) {}

  public void removed(IExtension[] extensions) {}

  public void added(IExtensionPoint[] points) {/* do nothing */}

  public void removed(IExtensionPoint[] points) {/* do nothing */}

  @SuppressWarnings("unchecked")
  public void addWatcher(final ServiceScope scope) {}

  public ExtensionImport findNextImport(final ExtensionImport prevImport, AttributeFilter filter) {
    return null;
  }
}
