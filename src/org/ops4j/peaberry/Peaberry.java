package org.ops4j.peaberry;

import org.ops4j.peaberry.internal.OSGiClassLoaderHook;
import org.ops4j.peaberry.internal.OSGiServiceDependencyBindingFactory;
import org.ops4j.peaberry.internal.OSGiServiceRegistrationBindingFactory;
import org.ops4j.peaberry.internal.OSGiServiceRegistryImpl;
import org.osgi.framework.BundleContext;
import com.google.inject.Binder;
import com.google.inject.ClassLoaderHook;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.Dependency;

public final class Peaberry {

  public static ClassLoaderHook getClassLoaderHook() {
    return new OSGiClassLoaderHook();
  }

  public static Module getModule(final BundleContext bc) {

    return new Module() {
      public void configure(Binder binder) {

        binder.bind(BundleContext.class).toInstance(bc);

        binder.bind(new AbstractMatcher<Dependency<?>>() {
          public boolean matches(Dependency<?> dependency) {
            return OSGiService.class.equals(dependency.getKey()
              .getAnnotationType());
          }
        }, new OSGiServiceDependencyBindingFactory());

        binder.bind(new AbstractMatcher<Dependency<?>>() {
          public boolean matches(Dependency<?> dependency) {
            return OSGiServiceRegistration.class.equals(dependency.getKey()
              .getAnnotationType());
          }
        }, new OSGiServiceRegistrationBindingFactory());

        binder.bind(OSGiServiceRegistry.class)
          .to(OSGiServiceRegistryImpl.class).in(Scopes.SINGLETON);
      }
    };
  }
}
