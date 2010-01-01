package org.ops4j.peaberry.activation.examples.config;

import static com.google.inject.matcher.Matchers.*;
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
    
    install(trackerModule(
        subclassesOf(ConfigRoot.class), 
        annotatedWith(Start.class).or(annotatedWith(Stop.class)).or(annotatedWith(Inject.class))));
  }
}
