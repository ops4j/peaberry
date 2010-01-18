package org.ops4j.peaberry.activation.builders;

import com.google.inject.Provider;

public interface ConfigurableKeyBuilder<T> { 
  Provider<T> named(String key);
}