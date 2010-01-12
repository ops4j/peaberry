package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.Configs.ConfigurationPidBuilder;
import org.ops4j.peaberry.activation.Configs.ConfigutrationKeyBuilder;
import org.ops4j.peaberry.activation.Configs.ConfigutraionDefaultValueBuilder;

import com.google.inject.Provider;

/*
 * FIX Use a Guice TypeLitera<T> instead?
 */
public class ConfigProviderBuilder<T> implements ConfigurationPidBuilder<T>, ConfigutrationKeyBuilder<T>,
    ConfigutraionDefaultValueBuilder<T> {
  
  private Class<T> type;
  private String pid;
  private String key;

  public ConfigProviderBuilder(Class<T> type) {
    this.type = type;
  }
  
  public ConfigutrationKeyBuilder<T> from(String pid) {
    this.pid = pid;
    return this;
  }

  public ConfigutraionDefaultValueBuilder<T> named(String key) {
    this.key = key;
    return this;
  }

  public Provider<T> optional(T val) {
    return new ConfigProvider<T>(type, pid, key, val);
  }

  public Provider<T> required() {
    return new ConfigProvider<T>(type, pid, key, null);
  }
}
