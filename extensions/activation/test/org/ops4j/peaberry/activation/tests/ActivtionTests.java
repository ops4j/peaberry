package org.ops4j.peaberry.activation.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import examples.activeroot.Counter;
import examples.serviceroot.Hello;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class ActivtionTests {
  private static final String SERVICEROOT_NAME = "examples.serviceroot";
  private static final String ACTIVEROOT_NAME = "examples.activeroot";
  private static final String EXTENDER_NAME = "org.ops4j.peaberry.activation";
  
  private static BundleContext bc;

  public static void setBundle(final Bundle bundle) {
    bc = bundle.getBundleContext();
  }
  
  private Bundle serviceRoot;
  private Bundle activeRoot;
  private Bundle activation;
  
  @BeforeClass
  public void findExampleBundles() {
    for (Bundle bnd : bc.getBundles()) {
      String name = bnd.getSymbolicName();
      
      if (SERVICEROOT_NAME.equals(name)) {
        serviceRoot = bnd;
      }
      else if (ACTIVEROOT_NAME.equals(name)) {
        activeRoot = bnd;
      }
      else if (EXTENDER_NAME.equals(name)) {
        activation = bnd;
      }
    }
  }
  
  @BeforeMethod
  public void resetBundles() throws BundleException {
    activation.stop();
    
    activeRoot.stop();
    
    serviceRoot.stop();
    Counter.reset();
  }
  
  @Test
  public void testServiceRoot() throws BundleException {
    activation.start();
    
    serviceRoot.start();
    assertEquals(bc.getServiceReference(Hello.class.getName()).getBundle(), serviceRoot);
  }
  
  @Test
  public void testActiveRoot() throws BundleException {
    activation.start();
    
    activeRoot.start();
    assertEquals(Counter.starts(), 1);
    
    activeRoot.stop();
    assertEquals(Counter.stops(), 1);
  }
  
  @Test
  public void testExtenderRestart() throws BundleException, InvalidSyntaxException {
    /* Extended bundles are active and waiting */
    serviceRoot.start();
    assertNull(bc.getServiceReference(Hello.class.getName()));
    
    activeRoot.start();
    assertEquals(Counter.starts(), 0);
    assertEquals(Counter.stops(), 0);
    
    /* Extender starts - must activate the bundles */
    activation.start();
    assertEquals(bc.getServiceReferences(Hello.class.getName(), null).length, 1);
    assertEquals(Counter.starts(), 1);
    
    /* Extender stops - must deactivate the bundles */
    activation.stop();
    assertNull(bc.getServiceReference(Hello.class.getName()));
    assertEquals(Counter.stops(), 1);
    
    /*
     * Extender starts again - must activate the bundles. No trace of previous
     * activations must exist.
     */
    activation.start();
    assertEquals(bc.getServiceReferences(Hello.class.getName(), null).length, 1);
    assertEquals(Counter.starts(), 2);
  }
}
