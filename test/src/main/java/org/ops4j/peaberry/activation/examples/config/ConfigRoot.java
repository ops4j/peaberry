package org.ops4j.peaberry.activation.examples.config;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ConfigRoot {
  public static final String CONF_PID = "test.pid";
  public static final String CONF_A = "test.pid" + ".a";
  public static final String CONF_B = "test.pid" + ".b";
  public static final String CONF_C = "test.pid" + ".c";
  
  @Inject
  public void setA(@Named(CONF_A) int a) {
  }

  @Inject
  public void setB(@Named(CONF_B) int b) {
  }

  @Inject
  public void setC(@Named(CONF_C) int c) {
  }
  
  @Start
  public void start() {
  }
  
  @Stop
  public void stop() {
  }
}
