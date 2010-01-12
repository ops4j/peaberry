package org.ops4j.peaberry.activation.examples.config;

import static com.google.inject.matcher.Matchers.*;
import static com.google.inject.name.Names.*;
import static org.ops4j.peaberry.activation.Configs.*;
import static org.ops4j.peaberry.activation.examples.config.ConfigRoot.*;
import static org.ops4j.peaberry.activation.invocations.Invocations.*;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

public class Config 
  extends AbstractModule {
  
  @Override
  protected void configure() {
    bind(ConfigRoot.class).in(Singleton.class);
    
    bind(Integer.class).annotatedWith(named(CONF_A)).toProvider(
        configurable(Integer.class).from(CONF_PID).named(CONF_A).required());
    
    bind(Integer.class).annotatedWith(named(CONF_B)).toProvider(
        configurable(Integer.class).from(CONF_PID).named(CONF_B).required());
    
    bind(Integer.class).annotatedWith(named(CONF_C)).toProvider(
        configurable(Integer.class).from(CONF_PID).named(CONF_C).required());
    
    install(trackerModule(
        subclassesOf(ConfigRoot.class), 
        annotatedWith(Start.class).or(annotatedWith(Stop.class)).or(annotatedWith(Inject.class))));
  }
}
