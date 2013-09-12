package org.ops4j.peaberry.activation.builders;

import com.google.inject.Provider;

public interface ConfigurableKeyBuilder<T> { 
  Provider<T> named(String key);

  /**
   * Returns a provider for all properties specified by PID. Can be bound to
   * {@code Dictionary<String,Object>} and {@code Map<String,Object>}. e.g.
   * <pre>
   *   bind(Dictionary.class).toProvider(
   *     Configurables.configurable(Dictionary.class).from(&quot;pid&quot;).all()
   *   );
   * </pre>
   * <pre>
   *   bind(Map.class).toProvider(
   *     Configurables.configurable(Map.class).from(&quot;pid&quot;).all()
   *   );
   * </pre>
   *
   * @since 1.4
   */
  Provider<T> all();
}