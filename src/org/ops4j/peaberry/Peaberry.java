/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.ops4j.peaberry.internal.OSGiClassLoaderHook;
import org.ops4j.peaberry.internal.OSGiServiceDependencyBindingFactory;
import org.ops4j.peaberry.internal.OSGiServiceRegistrationBindingFactory;
import org.ops4j.peaberry.internal.OSGiServiceRegistryImpl;
import org.osgi.framework.BundleContext;
import com.google.inject.Binder;
import com.google.inject.ClassLoaderHook;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.Dependency;
import com.google.inject.util.GuiceContainer;

/**
 * Peaberry utility class, provides OSGi service injection for Guice.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class Peaberry {

  private static final ClassLoaderHook LOADER_HOOK = new OSGiClassLoaderHook();

  /**
   * @return OSGi class loading hook
   */
  public static ClassLoaderHook getOSGiClassLoaderHook() {
    return LOADER_HOOK;
  }

  /**
   * Simple dependency matcher that matches on the binding annotation type.
   */
  private static final class AnnotationTypeMatcher extends
    AbstractMatcher<Dependency<?>> {

    private final Class<? extends Annotation> m_annotationType;

    /**
     * @param type annotation type to match on
     */
    public AnnotationTypeMatcher(Class<? extends Annotation> type) {
      m_annotationType = type;
    }

    /**
     * {@inheritDoc}
     */
    public boolean matches(Dependency<?> dependency) {
      return m_annotationType.equals(dependency.getKey().getAnnotationType());
    }
  }

  /**
   * @param bc current bundle context
   * @return OSGi service injection rules
   */
  public static Module getOSGiModule(final BundleContext bc) {

    return new Module() {
      public void configure(Binder binder) {

        binder.bind(BundleContext.class).toInstance(bc);

        // auto-bind OSGi service dependencies
        binder.bind(new AnnotationTypeMatcher(OSGiService.class),
          new OSGiServiceDependencyBindingFactory());

        // auto-bind OSGi service registrations
        binder.bind(new AnnotationTypeMatcher(OSGiServiceRegistration.class),
          new OSGiServiceRegistrationBindingFactory());

        binder.bind(OSGiServiceRegistry.class)
          .to(OSGiServiceRegistryImpl.class).in(Scopes.SINGLETON);
      }
    };
  }

  /**
   * Convenience method for constructing an OSGi enabled Guice injector
   * 
   * @param bc current bundle context
   * @param applicationModules application Guice modules
   * @return OSGi enabled Guice injector
   */
  public static Injector getOSGiInjector(BundleContext bc,
    Module... applicationModules) {
    return getOSGiInjector(bc, Arrays.asList(applicationModules));
  }

  /**
   * Convenience method for constructing an OSGi enabled Guice injector
   * 
   * @param bc current bundle context
   * @param applicationModules application Guice modules
   * @return OSGi enabled Guice injector
   */
  public static Injector getOSGiInjector(BundleContext bc,
    Collection<? extends Module> applicationModules) {

    ClassLoaderHook loaderHook = Peaberry.getOSGiClassLoaderHook();

    Collection<Module> modules = new ArrayList<Module>();
    modules.add(Peaberry.getOSGiModule(bc));
    modules.addAll(applicationModules);

    return GuiceContainer.createInjector(loaderHook, modules);
  }
}
