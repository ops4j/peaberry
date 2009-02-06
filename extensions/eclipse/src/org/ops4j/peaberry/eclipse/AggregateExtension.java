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
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class AggregateExtension
    implements IConfigurationElement {

  private final IExtension extension;

  public AggregateExtension(final IExtension extension) {
    this.extension = extension;
  }

  public Object createExecutableExtension(final String name) {
    throw new UnsupportedOperationException();
  }

  public String getAttribute(final String name) {
    return null;
  }

  @SuppressWarnings("deprecation")
  public String getAttributeAsIs(final String name) {
    return null;
  }

  public String[] getAttributeNames() {
    return new String[0];
  }

  public IConfigurationElement[] getChildren() {
    return extension.getConfigurationElements();
  }

  public IConfigurationElement[] getChildren(final String name) {
    final List<IConfigurationElement> children = new ArrayList<IConfigurationElement>();
    for (final IConfigurationElement e : extension.getConfigurationElements()) {
      children.add(e);
    }
    return children.toArray(new IConfigurationElement[children.size()]);
  }

  public IContributor getContributor() {
    return extension.getContributor();
  }

  public IExtension getDeclaringExtension() {
    return extension;
  }

  public String getName() {
    return extension.getLabel();
  }

  @SuppressWarnings("deprecation")
  public String getNamespace() {
    return extension.getNamespace();
  }

  public String getNamespaceIdentifier() {
    return extension.getNamespaceIdentifier();
  }

  public Object getParent() {
    throw new UnsupportedOperationException();
  }

  public String getValue() {
    return null;
  }

  @SuppressWarnings("deprecation")
  public String getValueAsIs() {
    throw new UnsupportedOperationException();
  }

  public boolean isValid() {
    return extension.isValid();
  }
}
