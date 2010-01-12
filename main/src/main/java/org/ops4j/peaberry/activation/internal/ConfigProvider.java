package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.PeaberryActivationException;

import com.google.inject.Provider;

public class ConfigProvider<T> implements Provider<T> {
  private final Class<T> type;
  private final String pid;
  private final String key;
  private T val;

  public ConfigProvider(Class<T> type, String pid, String key) {
    this.type = type;
    this.pid = pid;
    this.key = key;
  }

  public String pid() {
    return pid;
  }

  public String key() {
    return key;
  }
  
  public T get() {
    if (val == null) {
      throw new PeaberryActivationException("Configuration " + pid + ", key " + key + " is not set");
    }
    return val;
  }

  /*
   * FIX More validation? Automatic conversions? Can I re-use the Guice conversions?
   */
  public void set(Object val) {
    if (val != null) {
      this.val = type.cast(val);
    }
  }
}
