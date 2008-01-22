package com.google.inject.osgi.internal;

import com.google.inject.BindingFactory;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.spi.Dependency;

public class OSGiServiceDependencyBindingFactory
  implements BindingFactory<Object> {

  public <T> boolean bind(Dependency<T> dependency,
    LinkedBindingBuilder<T> linkedBindingBuilder) {
    // TODO Auto-generated method stub
    return false;
  }
}
