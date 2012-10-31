package org.ops4j.peaberry.activation.examples.renameconfig;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;
import org.ops4j.peaberry.activation.util.PeaberryActivationModule;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import static com.google.inject.matcher.Matchers.*;
import static org.ops4j.peaberry.activation.examples.renameconfig.RenamingConfigRoot.*;
import static com.google.inject.name.Names.*;
import static org.ops4j.peaberry.activation.invocations.Invocations.trackerModule;

public class Config extends PeaberryActivationModule {
  @Override
  protected void configure() {
    bind(RenamingConfigRoot.class).in(Singleton.class);
    
    bindConfigurable(Integer.class).annotatedWith(named(CONF_A_NAME)).from(CONF_PID).named(CONF_A_KEY);
    bindConfigurable(Integer.class).annotatedWith(ConfigValue.class).from(CONF_PID).named(CONF_B_KEY);
    bindConfigurable(Integer.class).from(CONF_PID).named(CONF_C_KEY);

    install(trackerModule(
        subclassesOf(RenamingConfigRoot.class), 
        annotatedWith(Start.class).or(annotatedWith(Stop.class)).or(annotatedWith(Inject.class))));
  }
}
