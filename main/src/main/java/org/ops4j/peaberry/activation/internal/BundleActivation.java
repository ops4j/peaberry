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
import org.ops4j.peaberry.activation.PeaberryActivationException;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.spi.BindingScopingVisitor;

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
        return false;
      }
    };

  private final Bundle bundle;
  private final Class<? extends Module> moduleClass;
  private final List<String> configPids; 

  private Map<String, Module> configModules;
  private List<BundleRoot<?>> roots;
  private Injector injector;

  @SuppressWarnings("unchecked")
  public BundleActivation(final Bundle bundle, final String module, final List<String> configs) {
    this.bundle = bundle;
    this.moduleClass = (Class<? extends Module>) Bundles.loadClass(bundle, module);
    this.configPids = configs;
    this.configModules = new HashMap<String, Module>();
  }

  @SuppressWarnings("unchecked")
  public void start(final Object lock) {
    /* Start listening for configuration changes */
    for (final String pid : configPids) {
      final BundleContext bc = bundle.getBundleContext();
      
      final Dictionary props = new Hashtable();
      props.put("service.pid", pid);
      props.put("service.bundleLocation", bundle.getLocation());
      
      bc.registerService(
        ManagedService.class.getName(), 
        new ManagedService() {
          public void updated(Dictionary props) throws ConfigurationException {
            synchronized (lock) {
              if (props == null) {
                deactivate();
              } else {
                configModules.put(pid, new ConfigurationModule(pid, props));
                
                if (configModules.size() == configPids.size()) {
                  deactivate();
                  activate();
                }
              }
            }
          }
        }, 
        props);
    }
    
    /* If there are no configurations activate now */
    if (configPids.isEmpty()) {
      activate();
    }
  }

  public void stop() {
    deactivate();
  }

  private void activate() {
    if (isActive()) {
      throw new PeaberryActivationException(Bundles.toString(bundle) + " already activated");
    }

    createInjector();
    createRoots();

    for (final BundleRoot<?> root : roots) {
      root.activate(injector);
    }
  }

  private void deactivate() {
    if (!isActive()) {
      return;
    }

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

    injector = null;
    roots = null;
  }

  private boolean isActive() {
    return injector != null;
  }

  private void createInjector() {
    List<Module> modules = new ArrayList<Module>();
    modules.add(Peaberry.osgiModule(bundle.getBundleContext()));
    modules.add(Reflection.create(moduleClass));
    modules.addAll(configModules.values());
    
    injector = Guice.createInjector(modules);
  }

  @SuppressWarnings("unchecked")
  private void createRoots() {
    final List<BundleRoot<?>> singletons = new ArrayList<BundleRoot<?>>();
    final List<BundleRoot<?>> exports = new ArrayList<BundleRoot<?>>();

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
}
