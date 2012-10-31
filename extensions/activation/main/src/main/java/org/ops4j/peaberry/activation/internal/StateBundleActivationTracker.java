package org.ops4j.peaberry.activation.internal;

import org.osgi.framework.Bundle;

/**
 * A trivial tracker used to trigger the initial attempt to start the bundle or
 * the final attempt to stop it. It is added to the list of trackers to
 * guarantee activation/deactivation will happen even if we do not need to track
 * any dynamic aspect of the OSGi environment.
 * 
 * This tracker guarantees that at all times both the BundleActivation and the
 * Bundle it manages are in an active state. If the activation is stopped
 * because the extender bundle is stopped this tracker will guarantee the
 * respective user bundle will also be deactivated.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class StateBundleActivationTracker implements BundleActivationTracker<Integer> {
  private final BundleActivation activation;
  private boolean started;
  
  public StateBundleActivationTracker(BundleActivation activation) {
    this.activation = activation;
  }
  
  public Integer getValue() {
    return activation.getBundle().getState();
  }

  public boolean canActivate() {
    return started && Bundle.ACTIVE == activation.getBundle().getState();
  }

  public void start() {
    synchronized (activation) {
      started = true;
      
      activation.update();
    }
  }

  public void stop() {
    synchronized (activation) {
      started = false;
      activation.update();
    }
  }
}
