package org.ops4j.peaberry.activation;

import com.google.inject.Provider;

import org.ops4j.peaberry.activation.internal.ConfigProviderBuilder;

public class Configs {
  private Configs() {
    /* static utility */
  }
  
  public interface ConfigPidBuilder<T> { 
    ConfigKeyBuilder<T> from(String pid);
  }
  
  public interface ConfigKeyBuilder<T> { 
    Provider<T> named(String key);
  }
  
  public static <T> ConfigPidBuilder<T> configurable(Class<T> type) {
    return new ConfigProviderBuilder<T>(type);
  }
}
