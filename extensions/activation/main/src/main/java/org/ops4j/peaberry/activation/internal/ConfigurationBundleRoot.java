package org.ops4j.peaberry.activation.internal;

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
        final Dictionary<String, Object> props = tracker.getValue();
        final ConfigurableProvider<?> prov = (ConfigurableProvider<?>) target.getProviderInstance();
        prov.set(props.get(prov.key()));
        return null;
      }
    };
  /** The last config we have obtained */
  private ConfigurationBundleActivationTracker tracker;
  
  public ConfigurationBundleRoot(List<Key<?>> keys, ConfigurationBundleActivationTracker tracker) {
    this.keys = keys;
    this.tracker = tracker;
  }
  
  public boolean canActivate() {
    return tracker.getValue() != null;
  }
  
  public void activate(Injector injector) {
    for (final Key<?> k : keys) {
      injector.getBinding(k).acceptTargetVisitor(mover);
    }
  }

  public void deactivate() {
  }
}
