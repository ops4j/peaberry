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

import static com.google.inject.Guice.createInjector;
import static org.eclipse.core.runtime.ContributorFactoryOSGi.resolve;
import static org.eclipse.core.runtime.RegistryFactory.getRegistry;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Status;

import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * An {@link IExecutableExtensionFactory} that looks for {@link Module} bindings
 * under the extension point named in the {@code inject} attribute. It creates a
 * new {@link Injector} based on the collection of modules and caches it against
 * the same extension point. It then uses this injector to create and inject the
 * extension instance.
 * <p>
 * To use this factory rename your current extension class attribute and replace
 * it with one that refers to this factory followed by your new class attribute,
 * then add the {@code inject} attribute identifying the module extension point.
 * 
 * <pre>{@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         class="example.ViewImpl"
 *         id="example.view"{@literal >}
 *   {@literal <}/view>
 * {@literal <}/extension{@literal >}</pre>
 * becomes:
 * 
 * <pre>{@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         inject="example.view.modules"
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory:key"
 *         key="example.ViewImpl"
 *         id="example.view"{@literal >}
 *   {@literal <}/view>
 * {@literal <}/extension{@literal >}</pre>
 * If no name is given after the factory class it is assumed to be "id":
 * 
 * <pre>{@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         inject="example.view.modules"
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory"
 *         id="example.ViewImpl"{@literal >}
 *   {@literal <}/view>
 * {@literal <}/extension{@literal >}</pre>
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class GuiceExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory {

  private static final ConcurrentHashMap<String, Injector> INJECTORS =
      new ConcurrentHashMap<String, Injector>();

  private String clazzAttributeName;
  private IConfigurationElement config;
  private IContributor contributor;

  public void setInitializationData(final IConfigurationElement config, final String name,
      final Object data) {

    // find class attribute in the configuration
    clazzAttributeName = mapClassAttribute(data);
    contributor = config.getContributor();

    this.config = config;
  }

  static String mapClassAttribute(final Object data) {
    // data is expected to refer to another attribute with the real class string
    return data instanceof String && !((String) data).isEmpty() ? (String) data : "id";
  }

  public static void reset(final String extensionPointId) {
    INJECTORS.remove(extensionPointId);
  }

  public static void reset() {
    INJECTORS.clear();
  }

  public Object create()
      throws CoreException {

    final String value = config.getAttribute(clazzAttributeName);
    if (null == value) {
      throw newCoreException("Missing IExecutableExtension adapter data");
    }

    final int n = value.indexOf(':');

    // separate name and data components from the attribute value
    final String clazzName = n < 0 ? value : value.substring(0, n);

    final Class<?> clazz;
    try {
      clazz = resolve(contributor).loadClass(clazzName);
    } catch (InvalidRegistryObjectException e) {
      throw newCoreException(e);
    } catch (ClassNotFoundException e) {
      throw newCoreException(e);
    }

    final String injectPointId = config.getAttribute("inject");
    if (null == injectPointId) {
      throw newCoreException("Configuration is missing inject attribute");
    }

    return getInjector(injectPointId).getInstance(clazz);
  }

  private Injector getInjector(final String injectPointId)
      throws CoreException {

    Injector injector = INJECTORS.get(injectPointId);
    if (null == injector) {

      final IConfigurationElement[] moduleConfigs =
          getRegistry().getConfigurationElementsFor(injectPointId);

      final Module[] modules = new Module[moduleConfigs.length];
      for (int i = 0; i < modules.length; i++) {
        modules[i] = (Module) moduleConfigs[i].createExecutableExtension("class");
      }

      final Injector newInjector = createInjector(modules);
      injector = INJECTORS.putIfAbsent(injectPointId, newInjector);
      if (null == injector) {
        return newInjector;
      }
    }
    return injector;
  }

  private CoreException newCoreException(final Throwable e) {
    return new CoreException(new Status(IStatus.ERROR, contributor.getName(), e.getMessage(), e));
  }

  private CoreException newCoreException(final String message) {
    return new CoreException(new Status(IStatus.ERROR, contributor.getName(), message));
  }
}
