/**
 * Copyright (C) 2010 Todor Boev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.peaberry.activation.examples.config;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ConfigRoot {
  public static final String CONF_PID = "test.pid";
  public static final String CONF_A = "a";
  public static final String CONF_B = "b";
  public static final String CONF_C = "c";
  
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
