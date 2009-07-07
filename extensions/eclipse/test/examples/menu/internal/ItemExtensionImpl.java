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

package examples.menu.internal;

import static org.testng.Assert.assertNotNull;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

import examples.menu.Item;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class ItemExtensionImpl
    implements IExecutableExtension, Item {

  String label = null;

  public String getLabel() {
    assertNotNull(label);
    return label;
  }

  public void setInitializationData(final IConfigurationElement config, final String name,
      final Object data) {
    label = "Initialized menu item (was: " + config.getAttribute("label") + ")";
  }
}
