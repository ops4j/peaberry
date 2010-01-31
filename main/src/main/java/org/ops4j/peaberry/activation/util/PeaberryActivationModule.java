package org.ops4j.peaberry.activation.util;

import org.ops4j.peaberry.builders.DecoratedServiceBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * Extends a regular Guice {@link AbstractModule} with convenient starter
 * methods for {@link ServiceBinder} and {@link ConfigurableBinder}.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 */
public abstract class PeaberryActivationModule extends AbstractModule {
  protected <T> DecoratedServiceBuilder<T> bindService(Class<T> type) {
    return ServiceBinder.service(binder(), Key.get(type));
  }

  protected <T> DecoratedServiceBuilder<T> bindService(TypeLiteral<T> type) {
    return ServiceBinder.service(binder(), Key.get(type));
  }

  protected <T> DecoratedServiceBuilder<T> bindService(Key<T> type) {
    return ServiceBinder.service(binder(), type);
  }
  
  protected <T> ConfigurableAnnotatedBuilder<T> bindConfigurable(Class<T> type) {
    return ConfigurableBinder.configurable(binder(), type);
  }
}
