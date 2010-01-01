package org.ops4j.peaberry.activation.tests;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import static org.ops4j.peaberry.activation.tests.Matchers.*;
import static org.ops4j.peaberry.activation.tests.TinyBundleProvisionOption.*;
import static org.osgi.framework.Constants.*;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.CompositeOption;

public class PeaberryConfiguration implements CompositeOption {
  private static final String INVOCATIONS_LOG_MODULE = 
    packageOf(org.ops4j.peaberry.activation.invocations.InvocationTracker.class);
  
  public Option[] getOptions() {
    return options(
      /* Install the peaberry runtime */
      mavenBundle().groupId("org.ops4j.peaberry.dependencies").artifactId("aopalliance").versionAsInProject(), 
      mavenBundle().groupId("org.ops4j.peaberry.dependencies").artifactId("javax.inject").versionAsInProject(), 
      mavenBundle().groupId("org.ops4j.peaberry.dependencies").artifactId("guice").versionAsInProject(), 
      mavenBundle().groupId("org.ops4j").artifactId("peaberry").versionAsInProject(),
      
      /* Build the invocations tracker bundle */
      tinyBundle()
       .set(BUNDLE_SYMBOLICNAME, INVOCATIONS_LOG_MODULE)
       .set(EXPORT_PACKAGE, INVOCATIONS_LOG_MODULE)
       .set(BUNDLE_ACTIVATOR, INVOCATIONS_LOG_MODULE + ".internal.Activator")
       .add(org.ops4j.peaberry.activation.invocations.InvocationTracker.class)
       .add(org.ops4j.peaberry.activation.invocations.InvocationListener.class)
       .add(org.ops4j.peaberry.activation.invocations.Invocations.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.Activator.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.FilteredInvocationListener.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.FilteredInvocationListenerDecorator.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.InvocationsModule.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.InvocationTrackerModule.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.InvocationTrackerImpl.class)
       .add(org.ops4j.peaberry.activation.invocations.internal.LoggingInterceptor.class)
       .build(withBnd()));
  }
}
