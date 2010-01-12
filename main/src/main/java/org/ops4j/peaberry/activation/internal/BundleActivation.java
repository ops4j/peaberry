/**
 * Copyright (C) 2009 Todor Boev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.peaberry.activation.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.spi.BindingScopingVisitor;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import com.google.inject.spi.ProviderInstanceBinding;

/**
 * Handles the activation of a single bundle.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class BundleActivation {
  private static final BindingScopingVisitor<Boolean> SINGLETONS =
    new BindingScopingVisitor<Boolean>() {
      public Boolean visitEagerSingleton() {
        return true;
      }

      public Boolean visitScope(final Scope scope) {
        return Scopes.SINGLETON == scope;
      }

      public Boolean visitScopeAnnotation(final Class<? extends Annotation> tag) {
        return Singleton.class == tag;
      }

      public Boolean visitNoScoping() {
        return Boolean.FALSE;
      }
    };

  private static final BindingTargetVisitor<Object, ConfigProvider<?>> CONFIGS = 
    new DefaultBindingTargetVisitor<Object, ConfigProvider<?>>() {
      @Override
      public ConfigProvider<?> visit(ProviderInstanceBinding<?> bind) {
        Provider<?> prov = bind.getProviderInstance();
        return (prov instanceof ConfigProvider<?>) ? (ConfigProvider<?>) prov : null;
      }
    };
    
  private class ConfigTracker implements ManagedService {
    private final Map<String, ConfigProvider<?>> providers = new HashMap<String, ConfigProvider<?>>();
    
    @SuppressWarnings("unchecked")
    public void updated(final Dictionary props) throws ConfigurationException {
      synchronized (BundleActivation.this) {
        if (props == null) {
          for (ConfigProvider<?> prov : providers.values()) {
            prov.set(null);
          }
        } else {
          for (Map.Entry<String, ConfigProvider<?>> entry : providers.entrySet()) {
            final String key = entry.getKey();
            final ConfigProvider<?> prov = entry.getValue();
            final Object val = props.get(key);
            
            prov.set(val);
          }
        }
        
        if (BundleActivation.this.canActivate()) {
          deactivate();
          activate();
        }
      }
    }
    
    public void add(ConfigProvider<?> prov) {
      providers.put(prov.key(), prov);
    }

    public boolean canActivate() {
      for (ConfigProvider<?> prov : providers.values()) {
        if (!prov.canActivate()) {
          return false;
        }
      }
      return true;
    }
  }
  
  private final Bundle bundle;
  private final Class<? extends Module> moduleClass;

  private Injector injector;
  private List<BundleRoot<?>> roots;
  private Map<String, ConfigTracker> configs;
  private boolean active;

  @SuppressWarnings("unchecked")
  public BundleActivation(final Bundle bundle, final String module) {
    this.bundle = bundle;
    this.moduleClass = (Class<? extends Module>) Bundles.loadClass(bundle, module);
  }

  public synchronized void start() {
    /* Create the Injector and introspect the configuration bindings */
    createInjector();
    createConfigs();
    
    /* Listen for configuration updates */
    for (final Map.Entry<String, ConfigTracker> e : configs.entrySet()) {
      final BundleContext bc = bundle.getBundleContext();
      
      final Dictionary<String, Object> props = new Hashtable<String, Object>();
      props.put("service.pid", e.getKey());
      props.put("service.bundleLocation", bundle.getLocation());
      
      bc.registerService(ManagedService.class.getName(), e.getValue(), props);
    }
    
    /* If we can activate now */
    if (canActivate()) {
      activate();
    }
  }

  public synchronized void stop() {
    deactivate();
  }

  private void activate() {
    if (isActive()) {
      throw new IllegalStateException(this + " already active");
    }
    active = true;
    
    createRoots();
    
    for (final BundleRoot<?> root : roots) {
      root.activate(injector);
    }
  }

  private void deactivate() {
    if (!isActive()) {
      return;
    }
    active = false;
    
    /*
     * Deactivate in reverse order. This should bring down the services before
     * we stop the active roots.
     */
    for (final ListIterator<BundleRoot<?>> iter = roots.listIterator(roots.size()); iter
        .hasPrevious();) {

      try {
        iter.previous().deactivate();
      } catch (final Exception e) {
        /*
         * FIX Accumulate these into an error list and at the end of the
         * deactivation toss an exception containing the list like Guice does
         */
        e.printStackTrace();
      }
    }

    roots = null;
  }

  private boolean isActive() {
    return active;
  }
  
  private boolean canActivate() {
    for (ConfigTracker trk : configs.values()) {
      if (!trk.canActivate()) {
        return false;
      }
    }
    return true;
  }

  private void createInjector() {
    injector = Guice.createInjector(
        Peaberry.osgiModule(bundle.getBundleContext()), Reflection.create(moduleClass));
  }

  @SuppressWarnings("unchecked")
  private void createRoots() {
    final List<BundleRoot<?>> exports = new ArrayList<BundleRoot<?>>();
    final List<BundleRoot<?>> singletons = new ArrayList<BundleRoot<?>>();

    /* Scan for singletons first */
    for (final Map.Entry<Key<?>, Binding<?>> e : injector.getBindings().entrySet()) {
      final Key<?> k = e.getKey();
      final Binding<?> b = e.getValue();
      
      /*
       * Exports are captured before singletons. In this way exports that have
       * been redundantly marked as singleton are treated correctly rather than
       * scanned for start/stop methods etc.
       */
      if (Export.class == k.getTypeLiteral().getRawType()) {
        exports.add(new ExportBundleRoot((Key<Export<?>>) k));
      } else if (b.acceptScopingVisitor(SINGLETONS)) {
        singletons.add(new SingletonBundleRoot((Key<Object>) k));
      }
    }

    /*
     * To get deterministic startup we activate the singletons before we export
     * some of them to control flow from other bundles.
     */
    singletons.addAll(exports);
    roots = singletons;
  }
  
  private void createConfigs() {
    configs = new HashMap<String, ConfigTracker>();
    
    for (final Map.Entry<Key<?>, Binding<?>> e : injector.getBindings().entrySet()) {
      final Binding<?> b = e.getValue();
      
      final ConfigProvider<?> prov = b.acceptTargetVisitor(CONFIGS);
      if (prov != null) {
        ConfigTracker tracker = configs.get(prov.pid());
        if (tracker == null) {
          tracker = new ConfigTracker();
          configs.put(prov.pid(), tracker);
        }
        
        tracker.add(prov);
      }
    }
  }
}
