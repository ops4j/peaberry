package com.google.inject.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class AbstractInjectedActivator
  implements BundleActivator {

  public final void start(BundleContext bc)
    throws Exception {
    // TODO: inject members
  }

  public final void stop(BundleContext bc)
    throws Exception {
    // TODO: any cleanup?
  }
}
