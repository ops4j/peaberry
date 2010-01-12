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
    
  private final Bundle bundle;
  private final Class<? extends Module> moduleClass;
  
  private List<BundleRoot> roots;
  private boolean active;

  @SuppressWarnings("unchecked")
  public BundleActivation(final Bundle bundle, final String module) {
    this.bundle = bundle;
    this.moduleClass = (Class<? extends Module>) Bundles.loadClass(bundle, module);
  }

  public synchronized void start() {
    createRoots();
    
    if (canActivate()) {
      activate();
    }
  }
  
  public synchronized void stop() {
    deactivate();
  }

  private void activate() {
    if (active) {
      throw new IllegalStateException(this + " already active");
    }
    active = true;
    
    final Injector injector = createInjector();
    
    for (final BundleRoot r : roots) {
      r.activate(injector);
    }
  }

  private void deactivate() {
    if (!active) {
      return;
    }
    active = false;
    
    /*
     * Deactivate in reverse order. This should bring down the services before
     * we stop the active roots.
     */
    for (final ListIterator<BundleRoot> iter = roots.listIterator(roots.size()); iter
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
  }

  private boolean canActivate() {
    for (BundleRoot r : roots) {
      if (!r.canActivate()) {
        return false;
      }
    }
    return true;
  }

  private Injector createInjector() {
    return Guice.createInjector(
        Peaberry.osgiModule(bundle.getBundleContext()), Reflection.create(moduleClass));
  }

  @SuppressWarnings("unchecked")
  private void createRoots() {
    final List<BundleRoot> exports = new ArrayList<BundleRoot>();
    final List<BundleRoot> singletons = new ArrayList<BundleRoot>();
    final Map<String, ConfigBundleRoot> configs = new HashMap<String, ConfigBundleRoot>();
    
    /* A temporary Injector used to introspect the configuration */
    final Injector injector = createInjector();
 
    for (final Map.Entry<Key<?>, Binding<?>> e : injector.getBindings().entrySet()) {
      final Key<?> key = e.getKey();
      final Binding<?> bind = e.getValue();
      
      /*
       * Exports and configs are captured before singletons. In this way exports
       * and configs that have been redundantly marked as singleton are treated
       * correctly rather than scanned for start/stop methods etc.
       */
      
      /* An export */
      if (Export.class == key.getTypeLiteral().getRawType()) {
        exports.add(new ExportBundleRoot((Key<Export<?>>) key));
      } 
      /* Config or singleton */
      else {
        final ConfigProvider<?> prov = bind.acceptTargetVisitor(CONFIGS);
        
        /* A config */
        if (prov != null) {
          final String pid = prov.pid();
          
          /* Create and register a new config root or just update an existing one */
          ConfigBundleRoot config = configs.get(pid);
          
          if (config == null) {
            config = new ConfigBundleRoot();
            configs.put(prov.pid(), config);
            
            /* Expose a ManagedService to track this config */
            final Dictionary props = new Hashtable();
            props.put("service.pid", pid);
            props.put("service.bundleLocation", bundle.getLocation());
            
            final ConfigBundleRoot updated = config;
            
            bundle.getBundleContext().registerService(
              ManagedService.class.getName(), 
              new ManagedService() {
                public void updated(final Dictionary props) {
                  synchronized (BundleActivation.this) {
                    if (props == null) {
                      return;
                    }
                    
                    updated.set(props);
                    
                    if (canActivate()) {
                      deactivate();
                      activate();
                    }
                  }
                }
              }, 
              props);
          }
          
          config.add(key);
        }
        /* A singleton */
        else if (bind.acceptScopingVisitor(SINGLETONS)) {
          singletons.add(new SingletonBundleRoot((Key<Object>) key));
        }
      }
    }

    this.roots = new ArrayList<BundleRoot>();
    /* First activate all configurations - so they setup their providers */
    roots.addAll(configs.values());
    /* Than activate all singletons - so they start doing work */
    roots.addAll(singletons);
    /* Finally activate all exports - so other bundles can use us */
    roots.addAll(exports);
  }
}
