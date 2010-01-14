package org.ops4j.peaberry.activation.internal;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import com.google.inject.spi.ProviderInstanceBinding;

public class ConfigurationBundleRoot implements BundleRoot {
  /** List of keys for which we must extract ConfigProvider and populate it */
  private final List<Key<?>> keys;
  /** Moves a config item from the Dictionary to a Guice provider */
  private final DefaultBindingTargetVisitor<Object, Object> mover = 
    new DefaultBindingTargetVisitor<Object, Object> () {
      public Object visit(ProviderInstanceBinding<?> target) {
        ConfigurableProvider<?> prov = (ConfigurableProvider<?>) target.getProviderInstance();
        prov.set(config.get(prov.key()));
        return null;
      }
    };
  /** The last config we have obtained */
  private Dictionary<String, Object> config;
  
  public ConfigurationBundleRoot() {
    this.keys = new ArrayList<Key<?>>();
  }
  
  public void add(final Key<?> key) {
    keys.add(key);
  }
  
  public void set(final Dictionary<String, Object> config) {
    this.config = config;
  }
  
  public boolean canActivate() {
    return config != null;
  }
  
  public void activate(Injector injector) {
    for (final Key<?> k : keys) {
      injector.getBinding(k).acceptTargetVisitor(mover);
    }
  }

  public void deactivate() {
  }
}
