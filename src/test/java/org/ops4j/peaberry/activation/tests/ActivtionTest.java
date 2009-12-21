package org.ops4j.peaberry.activation.tests;

import static com.google.inject.matcher.Matchers.*;
import static junit.framework.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import static org.ops4j.peaberry.activation.Constants.*;
import static org.osgi.framework.Constants.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AnnotatedElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Customizer;
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
  
  private static String toResourcePath(Class<?> c) {
    return c.getName().replace(".", "/") + ".class";
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
      mavenBundle().groupId("org.ops4j.peaberry.extensions").artifactId("peaberry.activation").versionAsInProject(),
      
      /* Build some tiny test bundles */
      provision(
        newBundle()
          .add(org.ops4j.peaberry.activation.examples.export.Config.class)
          .add(org.ops4j.peaberry.activation.examples.export.Hello.class)
          .add(org.ops4j.peaberry.activation.examples.export.HelloImpl.class)
          .set(BUNDLE_SYMBOLICNAME, EXPORT_MODULE)
          .set(EXPORT_PACKAGE, EXPORT_MODULE)
          .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.export.Config.class.getName())
          .build(withBnd()),
        newBundle()
          .add(org.ops4j.peaberry.activation.examples.singleton.Config.class)
          .add(org.ops4j.peaberry.activation.examples.singleton.SingletonRoot.class)
          .set(BUNDLE_SYMBOLICNAME, SINGLETON_MODULE)
          .set(EXPORT_PACKAGE, SINGLETON_MODULE)
          .set(BUNDLE_MODULE, org.ops4j.peaberry.activation.examples.singleton.Config.class.getName())
          .build(withBnd()),
        newBundle()
         .add(org.ops4j.peaberry.activation.invocations.InvocationLog.class)
         .add(org.ops4j.peaberry.activation.invocations.InvocationLogModule.class)
         .add(org.ops4j.peaberry.activation.invocations.internal.Activator.class)
         .add(org.ops4j.peaberry.activation.invocations.internal.InvocationLogImpl.class)
         .add(org.ops4j.peaberry.activation.invocations.internal.LoggingInterceptor.class)
         .set(BUNDLE_SYMBOLICNAME, INVOCATIONS_LOG_MODULE)
         .set(EXPORT_PACKAGE, INVOCATIONS_LOG_MODULE)
         .set(BUNDLE_ACTIVATOR, INVOCATIONS_LOG_MODULE + ".internal.Activator")
         .build(withBnd())),
         
      /* Remove the test bundle code from the test probe */
      new Customizer() {
        @Override
        public InputStream customizeTestProbe(InputStream probe) 
          throws IOException {
          
          return modifyBundle(probe)
            .removeHeader(EXPORT_PACKAGE)
            .removeHeader(DYNAMICIMPORT_PACKAGE)
            .set(IMPORT_PACKAGE,
                 "org.ops4j.peaberry.activation.invocations, " +
                 "org.ops4j.peaberry.activation.examples.export, " +
                 "org.ops4j.peaberry.activation.examples.singleton, " +
                 "com.google.inject; resolution:=optional, " +
                 "com.google.inject.binder; resolution:=optional, " +
                 "com.google.inject.matcher; resolution:=optional, " +
                 "org.aopalliance.intercept; resolution:=optional, " +
                 "junit.framework; resolution:=optional, " +
                 "org.junit; resolution:=optional, " +
                 "org.junit.runner; resolution:=optional, " +
                 "org.ops4j.pax.exam; resolution:=optional, " +
                 "org.ops4j.pax.exam.junit; resolution:=optional, " +
                 "org.ops4j.pax.exam.options; resolution:=optional, " +
                 "org.ops4j.pax.swissbox.tinybundles.core; resolution:=optional, " +
                 "org.ops4j.peaberry; resolution:=optional, " +
                 "org.ops4j.peaberry.activation; resolution:=optional, " +
                 "org.ops4j.peaberry.builders; resolution:=optional, " +
                 "org.ops4j.peaberry.util; resolution:=optional, " +
                 "org.osgi.framework")
             
             /* Remove the invocation log code */
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.invocations.InvocationLog.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.invocations.InvocationLogModule.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.invocations.internal.Activator.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.invocations.internal.InvocationLogImpl.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.invocations.internal.LoggingInterceptor.class))
            
             /* Remove the singleton example code */
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.examples.singleton.Config.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.examples.singleton.SingletonRoot.class))
            
             /* Remove the export example code */
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.examples.export.Config.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.examples.export.Hello.class))
            .removeResource(toResourcePath(org.ops4j.peaberry.activation.examples.export.HelloImpl.class))
            
            .build();
        }
      });
  }
  
  @Before
  public void setup() 
    throws BundleException {
    
    for (Bundle bnd : bc.getBundles()) {
      final String name = bnd.getSymbolicName();
      
      if (EXPORT_MODULE.equals(name)) {
        bnd.stop();
        exportRoot = bnd;
      } 
      else if (SINGLETON_MODULE.equals(name)) {
        bnd.stop();
        singletonRoot = bnd;
      } 
      else if (EXTENDER_MODULE.equals(name)) {
        bnd.stop();
        activation = bnd;
      } 
      else if (INVOCATIONS_LOG_MODULE.equals(name)) {
        log = (InvocationLog) bc.getService(bc.getServiceReference(InvocationLog.class.getName()));
        log.reset();
      }
    }
  }

  @Test
  public void testExportRoot() 
    throws BundleException {
    
    activation.start();
    
    exportRoot.start();
    assertEquals(exportRoot, bc.getServiceReference(Hello.class.getName()).getBundle());
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
    assertNull(bc.getServiceReference(Hello.class.getName()));

    singletonRoot.start();
    assertEquals(0, log.get(SINGLETON_ROOT, STARTS.or(STOPS)).size());

    /* Extender starts - must activate the bundles */
    activation.start();
    assertEquals(1, bc.getServiceReferences(Hello.class.getName(), null).length);
    assertEquals(1,log.get(SINGLETON_ROOT, STARTS).size());

    /* Extender stops - must deactivate the bundles */
    activation.stop();
    assertNull(bc.getServiceReference(Hello.class.getName()));
    assertEquals(1,log.get(SINGLETON_ROOT, STOPS).size());

    /*
     * Extender starts again - must activate the bundles. No trace of previous
     * activations must exist.
     */
    activation.start();
    assertEquals(1, bc.getServiceReferences(Hello.class.getName(), null).length);
    assertEquals(2, log.get(SINGLETON_ROOT, STARTS).size());
  }
}
