package org.ops4j.peaberry.activation.tests;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.lang.reflect.AnnotatedElement;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;
import org.ops4j.peaberry.activation.invocations.InvocationLog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.matcher.Matcher;

import examples.export.Hello;
import examples.singleton.SingletonRoot;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class ActivtionTests {
  private static final String EXPORTOOT_NAME = "examples.export";
  private static final String SINGLETONROOT_NAME = "examples.singleton";
  private static final String EXTENDER_NAME = "org.ops4j.peaberry.activation";

  private static final Matcher<? super Class<?>> SINGLETON_ROOT = subclassesOf(SingletonRoot.class);
  private static final Matcher<AnnotatedElement> STARTS = annotatedWith(Start.class);
  private static final Matcher<AnnotatedElement> STOPS = annotatedWith(Stop.class);

  private static BundleContext bc;

  public static void setBundle(final Bundle bundle) {
    bc = bundle.getBundleContext();
  }

  private Bundle exportRoot;
  private Bundle singletonRoot;
  private Bundle activation;

  @BeforeClass
  public void findExampleBundles() {
    for (Bundle bnd : bc.getBundles()) {
      String name = bnd.getSymbolicName();

      if (EXPORTOOT_NAME.equals(name)) {
        exportRoot = bnd;
      } else if (SINGLETONROOT_NAME.equals(name)) {
        singletonRoot = bnd;
      } else if (EXTENDER_NAME.equals(name)) {
        activation = bnd;
      }
    }
  }

  private InvocationLog log;

  @BeforeClass
  public void findInvocationLog() {
    log = (InvocationLog) bc.getService(bc.getServiceReference(InvocationLog.class.getName()));
  }

  @BeforeMethod
  public void resetBundles()
      throws BundleException {
    
    activation.stop();
    singletonRoot.stop();
    exportRoot.stop();

    log.reset();
  }

  @Test
  public void testExportRoot()
      throws BundleException {
    
    activation.start();

    exportRoot.start();
    assertEquals(bc.getServiceReference(Hello.class.getName()).getBundle(), exportRoot);
  }

  @Test
  public void testSingletonRoot()
      throws BundleException {
    
    activation.start();

    singletonRoot.start();
    assertEquals(log.get(SINGLETON_ROOT, STARTS).size(), 1);

    singletonRoot.stop();
    assertEquals(log.get(SINGLETON_ROOT, STOPS).size(), 1);
  }

  @Test
  public void testExtenderRestart()
      throws BundleException, InvalidSyntaxException {

    /* Extended bundles are active and waiting */
    exportRoot.start();
    assertNull(bc.getServiceReference(Hello.class.getName()));

    singletonRoot.start();
    assertEquals(log.get(SINGLETON_ROOT, STARTS.or(STOPS)).size(), 0);

    /* Extender starts - must activate the bundles */
    activation.start();
    assertEquals(bc.getServiceReferences(Hello.class.getName(), null).length, 1);
    assertEquals(log.get(SINGLETON_ROOT, STARTS).size(), 1);

    /* Extender stops - must deactivate the bundles */
    activation.stop();
    assertNull(bc.getServiceReference(Hello.class.getName()));
    assertEquals(log.get(SINGLETON_ROOT, STOPS).size(), 1);

    /*
     * Extender starts again - must activate the bundles. No trace of previous
     * activations must exist.
     */
    activation.start();
    assertEquals(bc.getServiceReferences(Hello.class.getName(), null).length, 1);
    assertEquals(log.get(SINGLETON_ROOT, STARTS).size(), 2);
  }
}
