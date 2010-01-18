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

import static com.google.inject.matcher.Matchers.*;
import static org.ops4j.peaberry.activation.examples.config.ConfigRoot.*;
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
    bind(ConfigRoot.class).in(Singleton.class);
    bindConfigurable(Integer.class).from(CONF_PID).named(CONF_A);
    bindConfigurable(Integer.class).from(CONF_PID).named(CONF_B);
    bindConfigurable(Integer.class).from(CONF_PID).named(CONF_C);
    
    install(trackerModule(
        subclassesOf(ConfigRoot.class), 
        annotatedWith(Start.class).or(annotatedWith(Stop.class)).or(annotatedWith(Inject.class))));
  }
}
