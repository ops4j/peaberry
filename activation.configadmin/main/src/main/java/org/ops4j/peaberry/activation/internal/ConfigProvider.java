package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.PeaberryActivationException;

import com.google.inject.Provider;

public class ConfigProvider<T> implements Provider<T> {
  private final Class<T> type;
  private final String pid;
  private final String key;
  private final T defval;
  private T val;

  public ConfigProvider(Class<T> type, String pid, String key, T defval) {
    this.type = type;
    this.pid = pid;
    this.defval = defval;
    this.key = key;
  }

  public T get() {
    if (val != null) {
      return val;
    }
    
    if (defval != null) {
      return defval;
    }
    
    throw new PeaberryActivationException("Configuration " + pid + ", key " + key + " is not set");
  }

  /*
   * FIX More validation? Automatic conversions? Can I re-use the Guice conversions?
   */
  public void set(Object val) {
    this.val = (val != null) ? type.cast(val) : null;
  }

  public String pid() {
    return pid;
  }

  public String key() {
    return key;
  }
  
  public boolean canActivate() {
    return val != null || defval != null;
  }
}
