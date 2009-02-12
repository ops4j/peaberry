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
import java.util.logging.Logger;

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
 * {@link IExecutableExtensionFactory} that looks for {@link Module} bindings
 * registered under the extension point named in the {@code inject} attribute.
 * It creates an {@link Injector} based on these bindings and uses it to build
 * the injected extension instance. Injectors will be re-used for extensions
 * with the same {@code inject} attribute value, unless the cache is forcibly
 * {@link #reset()} by the application.
 * <p>
 * To use this factory:
 * <ol>
 * <li>rename your class attribute, for example from "class=" to "key="</li>
 * <li>add "class=org.ops4j.peaberry.eclipse.GuiceExtensionFactory:key"</li>
 * <li>add an "inject" attribute identifying the module extension point</li>
 * </ol>
 * ... and sit back!
 * <p>
 * Here's a more detailed example, based on the standard RCP Mail Template:
 * 
 * <pre> {@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         class="example.ViewImpl"
 *         id="example.view" /{@literal >}
 * {@literal <}/extension{@literal >}</pre>
 * becomes:
 * 
 * <pre> {@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         inject="example.modules"
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory:key"
 *         key="example.ViewImpl"
 *         id="example.view" /{@literal >}
 * {@literal <}/extension{@literal >}
 * 
 * {@literal <}!-- then somewhere else --{@literal >}
 * 
 * {@literal <}extension point="example.modules"{@literal >}
 *   {@literal <}module class="example.ViewModule" /{@literal >}
 * {@literal <}/extension{@literal >}</pre>
 * If no name is given after the factory class it is assumed to be "id":
 * 
 * <pre> {@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         inject="example.modules"
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory"
 *         id="example.ViewImpl" /{@literal >}
 * {@literal <}/extension{@literal >}</pre>
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class GuiceExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory {

  private static final Logger LOGGER = Logger.getLogger(GuiceExtensionFactory.class.getName());

  // injectors are shared by extensions with the same inject attribute
  private static final ConcurrentHashMap<String, Injector> INJECTORS =
      new ConcurrentHashMap<String, Injector>();

  private IConfigurationElement config;
  private String clazzAttributeName;
  private IContributor contributor;

  public void setInitializationData(final IConfigurationElement config, final String name,
      final Object data) {

    this.config = config;

    // find class attribute in the configuration
    clazzAttributeName = mapClassAttribute(data);
    contributor = config.getContributor();
  }

  static String mapClassAttribute(final Object data) {
    // data is expected to refer to another attribute with the real class string
    return data instanceof String && !((String) data).isEmpty() ? (String) data : "id";
  }

  /**
   * Remove {@link Injector} based on the given {@link Module} extension point.
   * 
   * @param extensionPointId the module extension point
   */
  public static void reset(final String extensionPointId) {
    INJECTORS.remove(extensionPointId);
  }

  /**
   * Remove all {@link Injector}s.
   */
  public static void reset() {
    INJECTORS.clear();
  }

  public Object create()
      throws CoreException {

    final String value = config.getAttribute(clazzAttributeName);
    if (null == value) {
      throw newCoreException("Configuration is missing " + clazzAttributeName + " attribute");
    }

    final int n = value.indexOf(':');

    // separate name and data components from the attribute value
    final String clazzName = n < 0 ? value : value.substring(0, n);

    final Class<?> clazz;
    try {
      clazz = resolve(contributor).loadClass(clazzName);
    } catch (final InvalidRegistryObjectException e) {
      throw newCoreException(e);
    } catch (final ClassNotFoundException e) {
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

      if (moduleConfigs.length == 0) {
        throw newCoreException("No Guice modules registered under: " + injectPointId);
      }

      // load up the various modules...
      final Module[] modules = new Module[moduleConfigs.length];
      for (int i = 0; i < modules.length; i++) {
        modules[i] = (Module) moduleConfigs[i].createExecutableExtension("class");
      }

      // create injector (may end up throwing it away if we're too late)
      final Injector newInjector = createInjector(modules);
      injector = INJECTORS.putIfAbsent(injectPointId, newInjector);
      if (null == injector) {
        return newInjector;
      }
    }
    return injector;
  }

  private CoreException newCoreException(final Throwable e) {
    LOGGER.warning(e.getMessage());
    return new CoreException(new Status(IStatus.ERROR, contributor.getName(), e.getMessage(), e));
  }

  private CoreException newCoreException(final String message) {
    LOGGER.warning(message);
    return new CoreException(new Status(IStatus.ERROR, contributor.getName(), message));
  }
}
