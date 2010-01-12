package org.ops4j.peaberry.activation;

import com.google.inject.Provider;

import org.ops4j.peaberry.activation.internal.ConfigProviderBuilder;

public class Configs {
  private Configs() {
    /* static utility */
  }
  
  public interface ConfigurationPidBuilder<T> { 
    ConfigutrationKeyBuilder<T> from(String pid);
  }
  
  public interface ConfigutrationKeyBuilder<T> { 
    ConfigutraionDefaultValueBuilder<T> named(String key);
  }
  
  public interface ConfigutraionDefaultValueBuilder<T> { 
    Provider<T> required();
    
    Provider<T> optional(T val);
  }
  
  public static <T> ConfigurationPidBuilder<T> configurable(Class<T> type) {
    return new ConfigProviderBuilder<T>(type);
  }
}
