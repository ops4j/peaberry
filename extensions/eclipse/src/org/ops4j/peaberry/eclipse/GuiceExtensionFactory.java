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
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.eclipse.EclipseRegistry.eclipseRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * {@link IExecutableExtensionFactory} that creates an injected extension based
 * on the class named in the adapter data section. If the data section is empty
 * the class is assumed to be in the {@code id} attribute. The factory searches
 * the {@code org.ops4j.peaberry.eclipse.modules} extension for {@link Module}s
 * belonging to the same plug-in and uses them to create a new {@link Injector}.
 * This injector creates the injected extension and is then cached so it can be
 * re-used for other extensions in the same plug-in.
 * <p>
 * To use the factory put it in front of your class name in the extension XML.
 * Or replace your class with the factory and put your class in the {@code id}
 * attribute instead. Because the implementation will be injected based on the
 * bindings you could even replace your class name with one of its interfaces,
 * and that interface will then be used to lookup the correct implementation.
 * <p>
 * Here's a more detailed walkthrough, based on the example RCP Mail Template:
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
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory:example.ViewImpl"
 *         id="example.view" /{@literal >}
 * {@literal <}/extension{@literal >}
 * {@literal <}extension point="org.ops4j.peaberry.eclipse.modules"{@literal >}
 *   {@literal <}module class="example.ViewModule" /{@literal >}
 * {@literal <}/extension{@literal >}</pre>
 * Here's the same example with the class in the {@code id} attribute:
 * 
 * <pre> {@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory"
 *         id="example.ViewImpl" /{@literal >}
 * {@literal <}/extension{@literal >}
 * {@literal <}extension point="org.ops4j.peaberry.eclipse.modules"{@literal >}
 *   {@literal <}module class="example.ViewModule" /{@literal >}
 * {@literal <}/extension{@literal >}</pre>
 * and again, this time using an interface instead of the implementation:
 * 
 * <pre> {@literal <}extension point="org.eclipse.ui.views"{@literal >}
 *   {@literal <}view name="Message"
 *         allowMultiple="true"
 *         icon="icons/sample2.gif"
 *         class="org.ops4j.peaberry.eclipse.GuiceExtensionFactory:org.eclipse.ui.IViewPart"
 *         id="example.view" /{@literal >}
 * {@literal <}/extension{@literal >}
 * {@literal <}extension point="org.ops4j.peaberry.eclipse.modules"{@literal >}
 *   {@literal <}module class="example.ViewModule" /{@literal >}
 * {@literal <}/extension{@literal >}</pre>
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class GuiceExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory {

  public static final String POINT_ID = "org.ops4j.peaberry.eclipse.modules";

  private static final Logger LOGGER = Logger.getLogger(GuiceExtensionFactory.class.getName());

  // re-use injectors for extensions with the same contributor
  private static final Map<IContributor, Injector> INJECTORS =
      new HashMap<IContributor, Injector>();

  private IContributor contributor;
  private String clazzName;

  public void setInitializationData(final IConfigurationElement config, final String name,
      final Object data) {

    contributor = config.getContributor();

    // if there's no (string-based) adapter data then class must be under "id"
    clazzName = data instanceof String ? (String) data : config.getAttribute("id");
  }

  public Object create()
      throws CoreException {

    if (null == clazzName) {
      throw newCoreException("Configuration is missing class information");
    }

    final Class<?> clazz;
    try {
      clazz = resolve(contributor).loadClass(clazzName);
    } catch (final InvalidRegistryObjectException e) {
      throw newCoreException(e);
    } catch (final ClassNotFoundException e) {
      throw newCoreException(e);
    }

    return getInjector().getInstance(clazz);
  }

  /**
   * Remove any {@link Injector}s belonging to uninstalled bundles.
   */
  public static void cleanup() {
    synchronized (INJECTORS) { // LOCK
      for (final IContributor c : INJECTORS.keySet()) {
        if (null == resolve(c)) {
          INJECTORS.remove(c);
        }
      }
    } // UNLOCK
  }

  private Injector getInjector()
      throws CoreException {

    synchronized (INJECTORS) { // LOCK
      Injector injector = INJECTORS.get(contributor);
      if (null == injector) {

        final List<Module> modules = new ArrayList<Module>();

        // first add the OSGi service registry and Eclipse extension bindings
        modules.add(osgiModule(resolve(contributor).getBundleContext(), eclipseRegistry()));

        // now add any module extensions contributed by the current plug-in
        for (final IConfigurationElement e : getRegistry().getConfigurationElementsFor(POINT_ID)) {
          if (contributor.equals(e.getContributor())) {
            modules.add((Module) e.createExecutableExtension("class"));
          }
        }

        injector = createInjector(modules);
        INJECTORS.put(contributor, injector);
      }
      return injector;
    } // UNLOCK
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
