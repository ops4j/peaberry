package org.ops4j.peaberry.activation.internal;

/**
 * A box that can track some aspect of the dynamic OSGi environment and holds
 * it's up to date value.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 * @param <T>
 */
public interface BundleActivationTracker<T> {
  void start();
  
  void stop();
  
  boolean canActivate();
  
  T getValue();
}
