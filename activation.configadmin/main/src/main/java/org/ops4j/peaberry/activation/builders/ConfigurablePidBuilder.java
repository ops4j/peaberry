package org.ops4j.peaberry.activation.builders;

public interface ConfigurablePidBuilder<T> { 
  ConfigurableKeyBuilder<T> from(String pid);
}