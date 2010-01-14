package org.ops4j.peaberry.activation.util;

import org.ops4j.peaberry.activation.Configurables;
import org.ops4j.peaberry.activation.builders.ConfigurableKeyBuilder;
import org.ops4j.peaberry.activation.builders.ConfigurablePidBuilder;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Convenience binder used to avoid some repetitions that occur when using the
 * regular Peaberry Activation DSL.
 * 
 * Instead of
 * 
 * <pre>
 * bind(Integer.class).annotatedWith(named(&quot;key&quot;)).toProvider(
 *     configurable(Integer.class).from(&quot;pid&quot;).named(&quot;key&quot;));
 * </pre>
 * 
 * we have
 * 
 * <pre>
 * bindConfigurable(Integer.class).from(&quot;pid&quot;).named(&quot;key&quot;);
 * </pre>
 * 
 * Here we loose the flexibility to rename the configuration item. When this is
 * required you can use the longer expression outlined above.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 * @param <T>
 */
public class ConfigurableBinder<T> implements ConfigurablePidBuilder<T>, ConfigurableKeyBuilder<T> {
  private final Binder binder;
  private final Class<T> target;
  
  private ConfigurablePidBuilder<T> pidBuilder;
  private ConfigurableKeyBuilder<T> keyBuilder;
  
  public static <T> ConfigurablePidBuilder<T> configurable(final Binder binder, final Class<T> type) {
    return new ConfigurableBinder<T>(binder, type);
  }
  
  private ConfigurableBinder(final Binder binder, final Class<T> target) {
    this.binder = binder;
    this.target = target;
    this.pidBuilder = Configurables.configurable(target);
  }
  
  public ConfigurableKeyBuilder<T> from(final String pid) {
    keyBuilder = pidBuilder.from(pid);
    return this;
  }

  public Provider<T> named(final String key) {
    final Provider<T> prov = keyBuilder.named(key);
    binder.bind(target).annotatedWith(Names.named(key)).toProvider(prov);
    
    /* Do not allow the provider to escape - we bound it already */
    return null;
  }
}
