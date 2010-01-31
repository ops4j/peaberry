package org.ops4j.peaberry.activation.util;

import java.lang.annotation.Annotation;

import org.ops4j.peaberry.activation.builders.ConfigurablePidBuilder;

public interface ConfigurableAnnotatedBuilder<T> extends ConfigurablePidBuilder<T> {
  ConfigurablePidBuilder<T> annotatedWith(Class<? extends Annotation> markerType);

  ConfigurablePidBuilder<T> annotatedWith(Annotation marker);
}
