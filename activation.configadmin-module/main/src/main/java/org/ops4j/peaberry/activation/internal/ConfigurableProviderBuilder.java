package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.builders.ConfigurableKeyBuilder;
import org.ops4j.peaberry.activation.builders.ConfigurablePidBuilder;

import com.google.inject.Provider;

/*
 * FIX Use a Guice TypeLiteral<T> instead of Class<T>?
 */
public class ConfigurableProviderBuilder<T> implements ConfigurablePidBuilder<T>,
    ConfigurableKeyBuilder<T> {
  
  private Class<T> type;
  private String pid;

  public ConfigurableProviderBuilder(Class<T> type) {
    this.type = type;
  }
  
  public ConfigurableKeyBuilder<T> from(String pid) {
    this.pid = pid;
    return this;
  }

  public Provider<T> named(String key) {
    return new ConfigurableProvider<T>(type, pid, key);
  }
}
