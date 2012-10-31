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
package org.ops4j.peaberry.activation.examples.dualconfig;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class DualConfigRoot {
  public static final String CONF_PID_A = "test.a.pid";
  public static final String CONF_AA = "aa";
  public static final String CONF_AB = "ab";
  public static final String CONF_AC = "ac";
  
  public static final String CONF_PID_B = "test.b.pid";
  public static final String CONF_BA = "ba";
  public static final String CONF_BB = "bb";
  public static final String CONF_BC = "bc";
  
  @Inject
  public void setA(@Named(CONF_AA) int a, @Named(CONF_AB) int b, @Named(CONF_AC) int c) {
  }

  @Inject
  public void setB(@Named(CONF_BA) int a, @Named(CONF_BB) int b, @Named(CONF_BC) int c) {
  }
  
  @Start
  public void start() {
  }
  
  @Stop
  public void stop() {
  }
}
