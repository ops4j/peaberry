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
package org.ops4j.peaberry.activation.examples.renameconfig;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RenamingConfigRoot {
  public static final String CONF_PID = "test.pid";
  
  public static final String CONF_A_NAME = "a-name";
  public static final String CONF_A_KEY = "a-key";
  public static final String CONF_B_KEY = "b-key";
  public static final String CONF_C_KEY = "c-key";
  
  @Inject
  public void setRenamed(@Named(CONF_A_NAME) int val) {
  }

  @Inject
  public void setCustomAnnotation(@ConfigValue int val) {
  }
  
  @Inject
  public void setDefault(@Named(CONF_C_KEY) int val) {
  }
  
  @Start
  public void start() {
  }
  
  @Stop
  public void stop() {
  }
}
