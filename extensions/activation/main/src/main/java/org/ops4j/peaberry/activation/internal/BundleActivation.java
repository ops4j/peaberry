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

import static org.ops4j.peaberry.activation.internal.Reflection.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;
import org.osgi.framework.Bundle;

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
 * Handles the activation of a single bundle. This is the hub that joins
 * together {@link BundleActivationTracker}s with {@link BundleRoot}s. The
 * trackers monitor the OSGi environment and trigger the
 * {@link BundleActivation} to reevaluate the state of that environment when
 * it changes. When the evaluation is successful all {@link BundleRoot}s are
 * activated. When the evaluation is not good all {@link BundleRoot}s are
 * deactivated.
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
        return false;
      }
    };

  private static final BindingTargetVisitor<Object, ConfigurableProvider<?>> CONFIGS = 
    new DefaultBindingTargetVisitor<Object, ConfigurableProvider<?>>() {
      @Override
      public ConfigurableProvider<?> visit(ProviderInstanceBinding<?> bind) {
        Provider<?> prov = bind.getProviderInstance();
        return (prov instanceof ConfigurableProvider<?>) ? (ConfigurableProvider<?>) prov : null;
      }
    };
    
  private final Bundle bundle;
  private final Module module;
  private final List<BundleActivationTracker<?>> trackers;
  private final List<BundleRoot> roots;
  
  private boolean active;

  public BundleActivation(final Bundle bundle, final String moduleName) {
    this.bundle = bundle;
    
    @SuppressWarnings("unchecked")
    final Class<? extends Module> moduleClass = 
       (Class<? extends Module>) Bundles.loadClass(bundle, moduleName);
    this.module = Reflection.create(moduleClass);
    
    this.trackers = new ArrayList<BundleActivationTracker<?>>();
    this.roots = new ArrayList<BundleRoot>();
    
    /* A temporary Injector used to introspect the bindings */
    final Injector injector = createInjector();
    
    /* Make mutable set of bindings to be consumed by the createXXX methods */
    final Set<Entry<Key<?>, Binding<?>>> bindings = new HashSet<Entry<Key<?>,Binding<?>>>();
    bindings.addAll(injector.getBindings().entrySet());

    /*
     * Configs and Exports are captured before singletons. In this way if they
     * are redundantly marked as singletons they are not scanned for start/stop
     * methods etc.
     */
    createConfigurations(injector, bindings);
    createExports(injector, bindings);
    createSingletons(injector, bindings);
    
    /* Add a tracker that will trigger the initial update */
    trackers.add(new StateBundleActivationTracker(this));
  }

  public Bundle getBundle() {
    return bundle;
  }
  
  public synchronized void start() {
    for (BundleActivationTracker<?> t : trackers) {
      t.start();
    }
  }
  
  public synchronized void stop() {
    for (BundleActivationTracker<?> t : trackers) {
      t.stop();
    }
  }

  /**
   * Must be called by the BundleActivationTrackers while holding the lock of
   * this BundleActivation. Each tracker must first take the lock, than update
   * it's value, than call this method. The goal is to guarantee that this
   * method will always see a stable set of values for all trackers.
   */
  public void update() {
    deactivate();
    
    try {
      if (canActivate()) {
        activate();
      }
    } catch (Exception e) {
      // TODO Log this somehow
      e.printStackTrace();
      
      deactivate();
    }
  }
  
  private boolean canActivate() {
    for (BundleActivationTracker<?> t : trackers) {
      if (!t.canActivate()) {
        return false;
      }
    }
    return true;
  }
  
  private void activate() {
    if (active) {
      throw new IllegalStateException(Bundles.toString(bundle) + " already activated");
    }
    active = true;
    
    final Injector injector = createInjector();
    
    for (BundleRoot r : roots) {
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
    for (final ListIterator<BundleRoot> iter = roots.listIterator(roots.size()); iter.hasPrevious();) {
      try {
        iter.previous().deactivate();
      } catch (final Exception e) {
        /*
         * TODO Accumulate these into an error list and at the end of the
         * deactivation toss an exception containing the list like Guice does
         */
        e.printStackTrace();
      }
    }
  }

  private Injector createInjector() {
    return Guice.createInjector(Peaberry.osgiModule(bundle.getBundleContext()), module);
  }

  private void createExports(final Injector injector, final Set<Entry<Key<?>, Binding<?>>> entries) {
    for (final Iterator<Entry<Key<?>, Binding<?>>> iter = entries.iterator(); iter.hasNext();) {
      final Entry<Key<?>, Binding<?>> e = iter.next();
      final Key<?> key = e.getKey();
      
      if (Export.class == key.getTypeLiteral().getRawType()) {
        @SuppressWarnings("unchecked")
        Key<Export<?>> exportKey = (Key<Export<?>>) key;
        
        roots.add(new ExportBundleRoot(exportKey));
        
        iter.remove();
      } 
    }
  }
  
  private void createSingletons(final Injector injector, final Set<Entry<Key<?>, Binding<?>>> entries) {
    for (final Iterator<Entry<Key<?>, Binding<?>>> iter = entries.iterator(); iter.hasNext();) {
      final Entry<Key<?>, Binding<?>> e = iter.next();
      final Key<?> key = e.getKey();
      final Binding<?> bind = e.getValue();
      
      if (bind.acceptScopingVisitor(SINGLETONS)) {
        final Class<?> type = key.getTypeLiteral().getRawType();
        final List<Method> start = findMethods(type, Start.class);
        final List<Method> stop = findMethods(type, Stop.class);
        
        if (start.size() + stop.size() > 0) {
          @SuppressWarnings("unchecked")
          Key<Object> singletonKey = (Key<Object>) key;
          
          roots.add(new SingletonBundleRoot(singletonKey, start, stop));
          
          iter.remove();
        }
      } 
    }
  }
  
  private void createConfigurations(final Injector injector, final Set<Entry<Key<?>, Binding<?>>> entries) {
    /* Group the keys of configurable items by common configuration PIDs */
    final Map<String, List<Key<?>>> configs = new HashMap<String, List<Key<?>>>();
    
    for (final Iterator<Entry<Key<?>, Binding<?>>> iter = entries.iterator(); iter.hasNext();) {
      final Entry<Key<?>, Binding<?>> e = iter.next();
      final Key<?> key = e.getKey();
      final Binding<?> bind = e.getValue();
      
      final ConfigurableProvider<?> prov = bind.acceptTargetVisitor(CONFIGS);
      
      if (prov != null) {
        final String pid = prov.pid();
        
        List<Key<?>> list = configs.get(pid);
        if (list == null) {
          list = new ArrayList<Key<?>>();
          configs.put(prov.pid(), list);
        }
        list.add(key);
        
        iter.remove();
      }
    }
    
    /* Crate configuration roots and their respective trackers */
    for (Entry<String, List<Key<?>>> e : configs.entrySet()) {
      final String pid = e.getKey();
      final List<Key<?>> keys = e.getValue();
      
      final ConfigurationBundleActivationTracker tracker = new ConfigurationBundleActivationTracker(pid, this);
      final ConfigurationBundleRoot updated = new ConfigurationBundleRoot(keys, tracker);
      
      roots.add(updated);
      trackers.add(tracker);
    }
  }
}
