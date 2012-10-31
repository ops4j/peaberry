package org.ops4j.peaberry.activation.internal;

import org.ops4j.peaberry.activation.PeaberryActivationException;

import com.google.inject.Key;
import com.google.inject.Provider;

public class ConfigurableProvider<T> implements Provider<T> {
  private final Key<T> bindKey;
  private final String pid;
  private final String key;
  private T val;

  public ConfigurableProvider(Key<T> bindKey, String pid, String key) {
    this.bindKey = bindKey;
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
      throw new PeaberryActivationException("Configuration value PID: " + pid + ", Key: " + key + " is not set");
    }
    return val;
  }

  /*
   * FIX More validation? Automatic conversions? Can I re-use the Guice conversions?
   */
  @SuppressWarnings("unchecked")
  public void set(Object val) {
    if (val != null) {
      Class<? super T> bindType = bindKey.getTypeLiteral().getRawType();
      if (!bindType.isAssignableFrom(val.getClass())) {
        throw new PeaberryActivationException("Incompatible value type for PID: " + pid
            + ", Key: " + key + ". Expected " + bindType + ", but found " + val.getClass());
      }
      
      this.val = (T) bindType.cast(val);
    }
  }
}
