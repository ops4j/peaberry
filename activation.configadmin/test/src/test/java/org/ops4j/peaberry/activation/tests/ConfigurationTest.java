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

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import static org.ops4j.peaberry.activation.Constants.*;
import static org.ops4j.peaberry.activation.invocations.util.Matchers.*;
import static org.ops4j.peaberry.activation.tests.TinyBundleProvisionOption.*;
import static org.osgi.framework.Constants.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.peaberry.activation.examples.config.ConfigRoot;
import org.ops4j.peaberry.activation.examples.dualconfig.DualConfigRoot;
import org.ops4j.peaberry.activation.invocations.util.InvocationTracking;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.service.cm.ConfigurationAdmin;

import static org.ops4j.peaberry.activation.examples.config.ConfigRoot.*;

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
  
  @Configuration(extend = PeaberryConfiguration.class)
  public static Option[] configure() {
    return options(
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
      .noStart());
  }
  
  private Bundle configRoot;
  private Bundle dualConfigRoot;
  private Bundle activation;

  @Before
  public void setup() 
    throws BundleException {
    
    configRoot = getBundle(CONFIG_MODULE);
    dualConfigRoot = getBundle(DUAL_CONFIG_MODULE);
    activation = getBundle(ACTIVATION_MODULE);
  }

  @Test
  public void testBasicConfiguration() 
    throws Exception {
    
    /* Make a test configuration */
    @SuppressWarnings("unchecked")
    final Dictionary<String, Object> props = new Hashtable(); 
    props.put(CONF_A, 1);
    props.put(CONF_B, 2);
    props.put(CONF_C, 3);
    
    final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
    cm.getConfiguration(CONF_PID, configRoot.getLocation()).update(props);
    
    /* Consume it and wait for the startup to happen */
    call(new Callable<Void>() {
      public Void call() throws Exception {
        activation.start();
        configRoot.start();
        return null;
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
  public void testLateConfiguration() 
    throws Exception {
    
    /* Start the configuration consumer */
    activation.start();
    configRoot.start();
    
    /* Check that activation is now performed */
    assertNotInvoked(type(ConfigRoot.class), method("start"));
    
    /* Create the configuration and wait for the startup to happen */
    call(new Callable<Void>() {
      public Void call() throws Exception {
        @SuppressWarnings("unchecked")
        final Dictionary<String, Object> props = new Hashtable(); 
        props.put(CONF_A, 1);
        props.put(CONF_B, 2);
        props.put(CONF_C, 3);
        
        final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
        cm.getConfiguration(CONF_PID, configRoot.getLocation()).update(props);
        return null;
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
    props.put(CONF_A, 1);
    props.put(CONF_B, 2);
    props.put(CONF_C, 3);
    
    final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
    cm.getConfiguration(CONF_PID, configRoot.getLocation()).update(props);
    
    /* Start the configuration consumer */
    call(new Callable<Void>() {
      public Void call() throws Exception {
        activation.start();
        configRoot.start();
        return null;
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
    call(new Callable<Void>() {
      public Void call() throws Exception {
        @SuppressWarnings("unchecked")
        final Dictionary<String, Object> props = new Hashtable(); 
        props.put(CONF_A, 4);
        props.put(CONF_B, 5);
        props.put(CONF_C, 6);
        
        final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
        cm.getConfiguration(CONF_PID, configRoot.getLocation()).update(props);
        return null;
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
    props.put(CONF_A, 1);
    props.put(CONF_B, 2);
    props.put(CONF_C, 3);
    
    final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
    cm.getConfiguration(CONF_PID, configRoot.getLocation()).update(props);
    
    /* Start the configuration consumer */
    call(new Callable<Void>() {
      public Void call() throws Exception {
        activation.start();
        configRoot.start();
        return null;
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
    call(new Callable<Void>() {
      public Void call() throws Exception {
        final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
        cm.getConfiguration(CONF_PID, configRoot.getLocation()).delete();
        return null;
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
    
    final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
    cm.getConfiguration(DualConfigRoot.CONF_PID_A, dualConfigRoot.getLocation()).update(props);
    
    /* Create the second configuration - startup must happen */
    call(new Callable<Void>() {
      public Void call() throws Exception {
        @SuppressWarnings("unchecked")
        final Dictionary<String, Object> props = new Hashtable(); 
        props.put(DualConfigRoot.CONF_BA, 4);
        props.put(DualConfigRoot.CONF_BB, 5);
        props.put(DualConfigRoot.CONF_BC, 6);
        
        final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
        cm.getConfiguration(DualConfigRoot.CONF_PID_B, dualConfigRoot.getLocation()).update(props);
        return null;
      }
    })
    .andWait(type(DualConfigRoot.class), method("start"))
    .until(5000);
    
    /* Check that activation is now performed */
    assertInvoked(type(DualConfigRoot.class), method("setA", int.class, int.class, int.class), 1, 2, 3);
    assertInvoked(type(DualConfigRoot.class), method("setB", int.class, int.class, int.class), 4, 5, 6);
    
    resetInvocations();
    
    /* Delete the first configuration - de-activation must happen */
    call(new Callable<Void>() {
      public Void call() throws Exception {
        final ConfigurationAdmin cm = getService(ConfigurationAdmin.class);
        cm.getConfiguration(DualConfigRoot.CONF_PID_A, configRoot.getLocation()).delete();
        return null;
      }
    })
    .andWait(type(DualConfigRoot.class), method("stop"))
    .until(5000);
  }
}
