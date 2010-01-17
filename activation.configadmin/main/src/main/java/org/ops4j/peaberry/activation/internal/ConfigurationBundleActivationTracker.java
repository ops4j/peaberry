package org.ops4j.peaberry.activation.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

public class ConfigurationBundleActivationTracker implements BundleActivationTracker<Dictionary<String, Object>> {
  private final String pid;
  private final BundleActivation activation;
  
  private ServiceRegistration reg;
  private Dictionary<String, Object> props; 
  
  public ConfigurationBundleActivationTracker(String pid, BundleActivation activation) {
    this.pid = pid;
    this.activation = activation;
  }
  
  public void start() {
    final Bundle bundle = activation.getBundle();
    
    final Dictionary<String, Object> regprops = new Hashtable<String, Object>();
    regprops.put("service.pid", pid);
    regprops.put("service.bundleLocation", bundle.getLocation());
    
    reg = bundle.getBundleContext().registerService(
      ManagedService.class.getName(), 
      new ManagedService() {
        @SuppressWarnings("unchecked")
        public void updated(final Dictionary updated) {
          synchronized (activation) {
            props = updated;
            activation.update();
          }
        }
      }, 
      regprops);
  }
  
  public void stop() {
    props = null;
    
    if (Bundle.ACTIVE == activation.getBundle().getState()) {
      reg.unregister();
    }
  }
  
  public Dictionary<String, Object> getValue() {
    return props;
  }

  public boolean canActivate() {
    return props != null;
  }
}
