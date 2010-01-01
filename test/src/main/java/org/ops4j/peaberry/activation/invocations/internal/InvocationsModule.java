package org.ops4j.peaberry.activation.invocations.internal;

import static org.ops4j.peaberry.Peaberry.*;
import static org.ops4j.peaberry.util.TypeLiterals.*;

import org.ops4j.peaberry.activation.invocations.InvocationListener;

import com.google.inject.AbstractModule;

public class InvocationsModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(export(InvocationTrackerImpl.class)).toProvider(
        service(InvocationTrackerImpl.class).export());
    
    bind(iterable(InvocationListener.class)).toProvider(
        service(InvocationListener.class)
        .decoratedWith(new FilteredInvocationListenerDecorator())
        .multiple());
  }
}