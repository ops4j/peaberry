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
package org.ops4j.peaberry.activation.tests;

import static junit.framework.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import static org.ops4j.peaberry.activation.Constants.*;
import static org.ops4j.peaberry.activation.invocations.util.Matchers.*;
import static org.ops4j.peaberry.activation.tests.TinyBundleProvisionOption.*;
import static org.osgi.framework.Constants.*;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.peaberry.activation.examples.config.ConfigRoot;
import org.ops4j.peaberry.activation.examples.dualconfig.DualConfigRoot;
import org.ops4j.peaberry.activation.examples.renameconfig.RenamingConfigRoot;
import org.ops4j.peaberry.activation.invocations.util.InvocationTracking;
import org.ops4j.peaberry.activation.invocations.util.Proc;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
@RunWith(JUnit4TestRunner.class)
public class ConfigurationTest extends InvocationTracking {
  private static final String ACTIVATION_MODULE = 
    "org.ops4j.peaberry.extensions.peaberry.activation";
  private static final String CONFIG_MODULE = 
    packageOf(org.ops4j.peaberry.activation.examples.config.Config.class);
  private static final String DUAL_CONFIG_MODULE = 
    packageOf(org.ops4j.peaberry.activation.examples.dualconfig.Config.class);
  private static final String RENAME_CONFIG_MODULE = 
    packageOf(org.ops4j.peaberry.activation.examples.renameconfig.Config.class);
  
  @Configuration(extend = PeaberryConfiguration.class)
  public static Option[] configure() {
    return options(
      mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.configadmin").versionAsInProject(),
        
      tinyBundle()
       .set(BUNDLE_SYMBOLICNAME, CONFIG_MODULE)
       .set(EXPORT_PACKAGE, CONFIG_MODULE)
       .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.config.Config.class.getName())
       .add(org.ops4j.peaberry.activation.examples.config.Config.class)
       .add(org.ops4j.peaberry.activation.examples.config.ConfigRoot.class)
       .build(withBnd())
       .noStart(),
      tinyBundle()
      .set(BUNDLE_SYMBOLICNAME, DUAL_CONFIG_MODULE)
      .set(EXPORT_PACKAGE, DUAL_CONFIG_MODULE)
      .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.dualconfig.Config.class.getName())
      .add(org.ops4j.peaberry.activation.examples.dualconfig.Config.class)
      .add(org.ops4j.peaberry.activation.examples.dualconfig.DualConfigRoot.class)
      .build(withBnd())
      .noStart(),
      tinyBundle()
      .set(BUNDLE_SYMBOLICNAME, RENAME_CONFIG_MODULE)
      .set(EXPORT_PACKAGE, RENAME_CONFIG_MODULE)
      .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.renameconfig.Config.class.getName())
      .add(org.ops4j.peaberry.activation.examples.renameconfig.Config.class)
      .add(org.ops4j.peaberry.activation.examples.renameconfig.ConfigValue.class)
      .add(org.ops4j.peaberry.activation.examples.renameconfig.RenamingConfigRoot.class)
      .build(withBnd())
      .noStart());
  }
  
  private Bundle configRoot;
  private Bundle dualConfigRoot;
  private Bundle renamedConfigRoot;
  private Bundle activation;

  @Before
  public void setup() 
    throws BundleException {
    
    configRoot = getBundle(CONFIG_MODULE);
    dualConfigRoot = getBundle(DUAL_CONFIG_MODULE);
    renamedConfigRoot = getBundle(RENAME_CONFIG_MODULE);
    activation = getBundle(ACTIVATION_MODULE);
  }

  @Test
  public void testBasicConfiguration() 
    throws Exception {
    
    /* Make a test configuration */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable(); 
    props.put(ConfigRoot.CONF_A, 1);
    props.put(ConfigRoot.CONF_B, 2);
    props.put(ConfigRoot.CONF_C, 3);
    
    getConfiguration(ConfigRoot.CONF_PID).update(props);
    
    /* Consume it and wait for the startup to happen */
    call(new Proc() {
      public void run() throws Exception {
        activation.start();
        configRoot.start();
      }
    })
    .andWait(type(ConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check the result */
    assertInvoked(type(ConfigRoot.class), method("setA", int.class), 1);
    assertInvoked(type(ConfigRoot.class), method("setB", int.class), 2);
    assertInvoked(type(ConfigRoot.class), method("setC", int.class), 3);
  }
  
  @Test
  public void testRenamedConfiguration() 
    throws Exception {
    
    /* Make a test configuration */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable(); 
    props.put(RenamingConfigRoot.CONF_A_KEY, 1);
    props.put(RenamingConfigRoot.CONF_B_KEY, 2);
    props.put(RenamingConfigRoot.CONF_C_KEY, 3);
    
    getConfiguration(RenamingConfigRoot.CONF_PID).update(props);
    
    /* Consume it and wait for the startup to happen */
    call(new Proc() {
      public void run() throws Exception {
        activation.start();
        renamedConfigRoot.start();
      }
    })
    .andWait(type(RenamingConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check the result */
    assertInvoked(type(RenamingConfigRoot.class), method("setRenamed", int.class), 1);
    assertInvoked(type(RenamingConfigRoot.class), method("setCustomAnnotation", int.class), 2);
    assertInvoked(type(RenamingConfigRoot.class), method("setDefault", int.class), 3);
  }
  
  @Test
  public void testManagedServiceUnregistration() 
    throws Exception {
    
    activation.start();
    configRoot.start();
    
    assertNotNull(getReference(ManagedService.class));
    
    activation.stop();
    
    assertNull(getReference(ManagedService.class));
  }
  
  @Test
  public void testLateConfiguration() 
    throws Exception {
    
    /* Start the configuration consumer */
    activation.start();
    configRoot.start();
    
    /* Check that activation is now performed */
    assertNotInvoked(type(ConfigRoot.class), method("start"));
    
    /* Create the configuration and wait for the startup to happen */
    call(new Proc() {
      public void run() throws Exception {
        @SuppressWarnings("unchecked")
        final Dictionary<String, Object> props = new Hashtable(); 
        props.put(ConfigRoot.CONF_A, 1);
        props.put(ConfigRoot.CONF_B, 2);
        props.put(ConfigRoot.CONF_C, 3);
        
        getConfiguration(ConfigRoot.CONF_PID).update(props);
      }
    })
    .andWait(type(ConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check that activation is now performed */
    assertInvoked(type(ConfigRoot.class), method("setA", int.class), 1);
    assertInvoked(type(ConfigRoot.class), method("setB", int.class), 2);
    assertInvoked(type(ConfigRoot.class), method("setC", int.class), 3);
  }
  
  @Test
  public void testConfigurationChange() 
    throws Exception {
    
    /* Create the configuration and wait for the startup to happen */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable(); 
    props.put(ConfigRoot.CONF_A, 1);
    props.put(ConfigRoot.CONF_B, 2);
    props.put(ConfigRoot.CONF_C, 3);
    
    getConfiguration(ConfigRoot.CONF_PID).update(props);
    
    /* Start the configuration consumer */
    call(new Proc() {
      public void run() throws Exception {
        activation.start();
        configRoot.start();
      }
    })
    .andWait(type(ConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check that activation is now performed */
    assertInvoked(type(ConfigRoot.class), method("setA", int.class), 1);
    assertInvoked(type(ConfigRoot.class), method("setB", int.class), 2);
    assertInvoked(type(ConfigRoot.class), method("setC", int.class), 3);
    
    resetInvocations();
    
    /* Change the configuration and wait for a restart */
    call(new Proc() {
      public void run() throws Exception {
        @SuppressWarnings("unchecked")
        final Dictionary<String, Object> props = new Hashtable(); 
        props.put(ConfigRoot.CONF_A, 4);
        props.put(ConfigRoot.CONF_B, 5);
        props.put(ConfigRoot.CONF_C, 6);
        
        getConfiguration(ConfigRoot.CONF_PID).update(props);
      }
    })
    .andWait(type(ConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check that the new configuration is set */
    assertInvoked(type(ConfigRoot.class), method("setA", int.class), 4);
    assertInvoked(type(ConfigRoot.class), method("setB", int.class), 5);
    assertInvoked(type(ConfigRoot.class), method("setC", int.class), 6);
  }
  
  @Test
  public void testConfigurationDeletion() 
    throws Exception {
    
    /* Create the configuration and wait for the startup to happen */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable(); 
    props.put(ConfigRoot.CONF_A, 1);
    props.put(ConfigRoot.CONF_B, 2);
    props.put(ConfigRoot.CONF_C, 3);
    
    getConfiguration(ConfigRoot.CONF_PID).update(props);
    
    /* Start the configuration consumer */
    call(new Proc() {
      public void run() throws Exception {
        activation.start();
        configRoot.start();
      }
    })
    .andWait(type(ConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check that activation is now performed */
    assertInvoked(type(ConfigRoot.class), method("setA", int.class), 1);
    assertInvoked(type(ConfigRoot.class), method("setB", int.class), 2);
    assertInvoked(type(ConfigRoot.class), method("setC", int.class), 3);
    
    resetInvocations();
    
    /* Delete the configuration */
    call(new Proc() {
      public void run() throws Exception {
        getConfiguration(ConfigRoot.CONF_PID).delete();
      }
    })
    .andWait(type(ConfigRoot.class), method("stop"))
    .until(5000);
  }
  
  @Test
  public void testDualConfiguration() 
    throws Exception {
    
    /* Start everything */
    activation.start();
    dualConfigRoot.start();
    
    /* Create the first configuration - startup must not happen */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable(); 
    props.put(DualConfigRoot.CONF_AA, 1);
    props.put(DualConfigRoot.CONF_AB, 2);
    props.put(DualConfigRoot.CONF_AC, 3);
    
    getConfiguration(DualConfigRoot.CONF_PID_A).update(props);
    
    /* Create the second configuration - startup must happen */
    call(new Proc() {
      public void run() throws Exception {
        @SuppressWarnings("unchecked")
        final Dictionary<String, Object> props = new Hashtable(); 
        props.put(DualConfigRoot.CONF_BA, 4);
        props.put(DualConfigRoot.CONF_BB, 5);
        props.put(DualConfigRoot.CONF_BC, 6);
        
        getConfiguration(DualConfigRoot.CONF_PID_B).update(props);
      }
    })
    .andWait(type(DualConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check that activation is now performed */
    assertInvoked(type(DualConfigRoot.class), method("setA", int.class, int.class, int.class), 1, 2, 3);
    assertInvoked(type(DualConfigRoot.class), method("setB", int.class, int.class, int.class), 4, 5, 6);
    
    resetInvocations();
    
    /* Delete the first configuration - de-activation must happen */
    call(new Proc() {
      public void run() throws Exception {
        getConfiguration(DualConfigRoot.CONF_PID_A).delete();
      }
    })
    .andWait(type(DualConfigRoot.class), method("stop"))
    .until(5000);
  }
  
  private org.osgi.service.cm.Configuration getConfiguration(String pid) throws IOException {
    final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
    return cm.getConfiguration(pid, null);    
  }
}
