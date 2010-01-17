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

import static com.google.inject.matcher.Matchers.*;
import static org.ops4j.peaberry.activation.examples.dualconfig.DualConfigRoot.*;
import static org.ops4j.peaberry.activation.invocations.Invocations.*;

import org.ops4j.peaberry.activation.Start;
import org.ops4j.peaberry.activation.Stop;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import org.ops4j.peaberry.activation.util.PeaberryActivationModule;

public class Config 
  extends PeaberryActivationModule {
  
  @Override
  protected void configure() {
    bind(DualConfigRoot.class).in(Singleton.class);
    
    bindConfigurable(Integer.class).from(CONF_PID_A).named(CONF_AA);
    bindConfigurable(Integer.class).from(CONF_PID_A).named(CONF_AB);
    bindConfigurable(Integer.class).from(CONF_PID_A).named(CONF_AC);
    
    bindConfigurable(Integer.class).from(CONF_PID_B).named(CONF_BA);
    bindConfigurable(Integer.class).from(CONF_PID_B).named(CONF_BB);
    bindConfigurable(Integer.class).from(CONF_PID_B).named(CONF_BC);
    
    install(trackerModule(
        subclassesOf(DualConfigRoot.class), 
        annotatedWith(Start.class).or(annotatedWith(Stop.class)).or(annotatedWith(Inject.class))));
  }
}
