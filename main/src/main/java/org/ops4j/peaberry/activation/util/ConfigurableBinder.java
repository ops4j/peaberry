package org.ops4j.peaberry.activation.util;

import java.lang.annotation.Annotation;

import org.ops4j.peaberry.activation.Configurables;
import org.ops4j.peaberry.activation.builders.ConfigurableKeyBuilder;
import org.ops4j.peaberry.activation.builders.ConfigurablePidBuilder;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
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
public class ConfigurableBinder<T> implements ConfigurablePidBuilder<T>, ConfigurableKeyBuilder<T>,
    ConfigurableAnnotatedBuilder<T> {
  
  private final Binder binder;
  
  private TypeLiteral<T> target;
  private Key<T> bindKey;
  private ConfigurablePidBuilder<T> pidBuilder;
  private ConfigurableKeyBuilder<T> keyBuilder;
  
  public static <T> ConfigurableAnnotatedBuilder<T> configurable(final Binder binder, final Class<T> type) {
    return new ConfigurableBinder<T>(binder, TypeLiteral.get(type));
  }
  
  public static <T> ConfigurableAnnotatedBuilder<T> configurable(final Binder binder, final TypeLiteral<T> type) {
    return new ConfigurableBinder<T>(binder, type);
  }
  
  public static <T> ConfigurableAnnotatedBuilder<T> configurable(final Binder binder, final Key<T> bindKey) {
    return new ConfigurableBinder<T>(binder, bindKey);
  }
  
  private ConfigurableBinder(final Binder binder, final TypeLiteral<T> target) {
    this.binder = binder;
    this.target = target;
    this.pidBuilder = Configurables.configurable(target);
  }
  
  private ConfigurableBinder(final Binder binder, final Key<T> bindKey) {
    this.binder = binder;
    this.bindKey = bindKey;
    this.pidBuilder = Configurables.configurable(bindKey);
  }
  
  public ConfigurablePidBuilder<T> annotatedWith(final Class<? extends Annotation> markerType) {
    bindKey = Key.get(target, markerType); 
    return this;
  }

  public ConfigurablePidBuilder<T> annotatedWith(final Annotation marker) {
    bindKey = Key.get(target, marker);
    return this;
  }
  
  public ConfigurableKeyBuilder<T> from(final String pid) {
    keyBuilder = pidBuilder.from(pid);
    return this;
  }

  public Provider<T> named(final String key) {
    if (bindKey == null) {
      bindKey = Key.get(target, Names.named(key)); 
    }
    
    final Provider<T> prov = keyBuilder.named(key);
    
    binder.bind(bindKey).toProvider(prov);
    
    /* Do not allow the provider to escape - we bound it already */
    return null;
  }
}
