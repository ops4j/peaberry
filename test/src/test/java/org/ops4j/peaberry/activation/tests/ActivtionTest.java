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

import static com.google.inject.matcher.Matchers.*;
import static junit.framework.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import static org.ops4j.peaberry.activation.Constants.*;
import static org.ops4j.peaberry.activation.tests.TinyBundleProvisionOption.*;
import static org.osgi.framework.Constants.*;

import java.lang.reflect.AnnotatedElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;
import org.ops4j.peaberry.activation.examples.export.Hello;
import org.ops4j.peaberry.activation.examples.singleton.SingletonRoot;
import org.ops4j.peaberry.activation.invocations.InvocationLog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.google.inject.matcher.Matcher;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
@RunWith(JUnit4TestRunner.class)
public class ActivtionTest {
  private static final String EXTENDER_MODULE = 
    "org.ops4j.peaberry.extensions.peaberry.activation";
  private static final String EXPORT_MODULE = 
    packageOf(org.ops4j.peaberry.activation.examples.export.Config.class);
  private static final String SINGLETON_MODULE = 
    packageOf(org.ops4j.peaberry.activation.examples.singleton.Config.class);
  private static final String INVOCATIONS_LOG_MODULE = 
    packageOf(org.ops4j.peaberry.activation.invocations.InvocationLog.class);
  
  private static String packageOf(Class<?> c) {
    return c.getPackage().getName();
  }
  
  private static final Matcher<? super Class<?>> SINGLETON_ROOT = subclassesOf(SingletonRoot.class);
  private static final Matcher<AnnotatedElement> STARTS = annotatedWith(Start.class);
  private static final Matcher<AnnotatedElement> STOPS = annotatedWith(Stop.class);

  @Inject
  private BundleContext bc;

  private InvocationLog log;
  private Bundle exportRoot;
  private Bundle singletonRoot;
  private Bundle activation;

  @Configuration
  public Option[] configure() {
    return options(
      /* Install the peaberry runtime */
      mavenBundle().groupId("org.ops4j.peaberry.dependencies").artifactId("aopalliance").versionAsInProject(), 
      mavenBundle().groupId("org.ops4j.peaberry.dependencies").artifactId("javax.inject").versionAsInProject(), 
      mavenBundle().groupId("org.ops4j.peaberry.dependencies").artifactId("guice").versionAsInProject(), 
      mavenBundle().groupId("org.ops4j").artifactId("peaberry").versionAsInProject(),
      mavenBundle().groupId("org.ops4j.peaberry.extensions").artifactId("peaberry.activation").versionAsInProject().noStart(),
      
      /* Build some test bundles on the fly */
      tinyBundle()
       .set(BUNDLE_SYMBOLICNAME, EXPORT_MODULE)
       .set(EXPORT_PACKAGE, EXPORT_MODULE)
       .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.export.Config.class.getName())
       .add(org.ops4j.peaberry.activation.examples.export.Config.class)
       .add(org.ops4j.peaberry.activation.examples.export.Hello.class)
       .add(org.ops4j.peaberry.activation.examples.export.HelloImpl.class)
       .build(withBnd())
       .noStart(),
      tinyBundle()
       .set(BUNDLE_SYMBOLICNAME, SINGLETON_MODULE)
       .set(EXPORT_PACKAGE, SINGLETON_MODULE)
       .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.singleton.Config.class.getName())
       .add(org.ops4j.peaberry.activation.examples.singleton.Config.class)
       .add(org.ops4j.peaberry.activation.examples.singleton.SingletonRoot.class)
       .build(withBnd())
       .noStart(),
      tinyBundle()
       .set(BUNDLE_SYMBOLICNAME, INVOCATIONS_LOG_MODULE)
       .set(EXPORT_PACKAGE, INVOCATIONS_LOG_MODULE)
       .set(BUNDLE_ACTIVATOR, INVOCATIONS_LOG_MODULE + ".internal.Activator")
       .add(org.ops4j.peaberry.activation.invocations.InvocationLog.class)
       .add(org.ops4j.peaberry.activation.invocations.InvocationLogModule.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.Activator.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.InvocationLogImpl.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.LoggingInterceptor.class)
       .build(withBnd()));
  }
  
  @Before
  public void setup() 
    throws BundleException {
    
    for (Bundle bnd : bc.getBundles()) {
      final String name = bnd.getSymbolicName();
      
      if (EXPORT_MODULE.equals(name)) {
        exportRoot = bnd;
      } 
      else if (SINGLETON_MODULE.equals(name)) {
        singletonRoot = bnd;
      } 
      else if (EXTENDER_MODULE.equals(name)) {
        activation = bnd;
      } 
      else if (INVOCATIONS_LOG_MODULE.equals(name)) {
        log = getService(InvocationLog.class);
      }
    }
  }

  @Test
  public void testExportRoot() 
    throws BundleException {
    
    activation.start();
    assertNull(getReference(Hello.class));
    
    exportRoot.start();
    assertEquals(exportRoot, getReference(Hello.class).getBundle());
  }

  @Test
  public void testSingletonRoot() 
    throws BundleException {
    
    activation.start();
    
    singletonRoot.start();
    assertEquals(1,log.get(SINGLETON_ROOT, STARTS).size());

    singletonRoot.stop();
    assertEquals(1,log.get(SINGLETON_ROOT, STOPS).size());
  }

  @Test
  public void testExtenderRestart() 
    throws BundleException, InvalidSyntaxException {
    
    /* Extended bundles are active and waiting */
    exportRoot.start();
    assertNull(getReference(Hello.class));

    singletonRoot.start();
    assertEquals(0, log.get(SINGLETON_ROOT, STARTS.or(STOPS)).size());

    /* Extender starts - must activate the bundles */
    activation.start();
    assertEquals(1, getReferenceList(Hello.class).length);
    assertEquals(1, log.get(SINGLETON_ROOT, STARTS).size());

    /* Extender stops - must deactivate the bundles */
    activation.stop();
    assertNull(getReference(Hello.class));
    assertEquals(1,log.get(SINGLETON_ROOT, STOPS).size());

    /*
     * Extender starts again - must activate the bundles. No trace of previous
     * activations must exist.
     */
    activation.start();
    assertEquals(1, getReferenceList(Hello.class).length);
    assertEquals(2, log.get(SINGLETON_ROOT, STARTS).size());
  }
  
  @SuppressWarnings("unchecked")
  private <T> T getService(Class<T> type) {
    ServiceReference ref = getReference(type); 
    assertNotNull("Can't obtain service reference " + type.getName(), ref);
    
    T serv = (T) bc.getService(ref);
    assertNotNull("Can't obtain service " + type.getName(), serv);
    
    return serv;
  }
  
  private ServiceReference getReference(Class<?> type) {
    return bc.getServiceReference(type.getName());
  }
  
  private ServiceReference[] getReferenceList(Class<?> type) {
    try {
      return bc.getServiceReferences(type.getName(), null);
    } catch (InvalidSyntaxException e) {
      throw new RuntimeException("Unexpected", e);
    }
  }
}
