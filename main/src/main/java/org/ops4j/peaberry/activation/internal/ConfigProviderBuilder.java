package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.Configs.ConfigKeyBuilder;
import org.ops4j.peaberry.activation.Configs.ConfigPidBuilder;

import com.google.inject.Provider;

/*
 * FIX Use a Guice TypeLiteral<T> instead of Class<T>?
 */
public class ConfigProviderBuilder<T> implements ConfigPidBuilder<T>,
    ConfigKeyBuilder<T> {
  
  private Class<T> type;
  private String pid;

  public ConfigProviderBuilder(Class<T> type) {
    this.type = type;
  }
  
  public ConfigKeyBuilder<T> from(String pid) {
    this.pid = pid;
    return this;
  }

  public Provider<T> named(String key) {
    return new ConfigProvider<T>(type, pid, key);
  }
}
