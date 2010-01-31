package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.builders.ConfigurableKeyBuilder;
import org.ops4j.peaberry.activation.builders.ConfigurablePidBuilder;

import com.google.inject.Key;
import com.google.inject.Provider;

public class ConfigurableProviderBuilder<T> implements ConfigurablePidBuilder<T>,
    ConfigurableKeyBuilder<T> {
  
  private Key<T> bindKey;
  private String pid;

  public ConfigurableProviderBuilder(Key<T> bindKey) {
    this.bindKey = bindKey;
  }
  
  public ConfigurableKeyBuilder<T> from(String pid) {
    this.pid = pid;
    return this;
  }

  public Provider<T> named(String key) {
    return new ConfigurableProvider<T>(bindKey, pid, key);
  }
}
