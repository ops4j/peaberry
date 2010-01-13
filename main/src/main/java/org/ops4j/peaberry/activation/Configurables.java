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
package org.ops4j.peaberry.activation;


import org.ops4j.peaberry.activation.builders.ConfigurablePidBuilder;
import org.ops4j.peaberry.activation.internal.ConfigProviderBuilder;

/**
 * A starting point for the fluent API used for config item providers.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class Configurables {
  private Configurables() {
    /* static utility */
  }
  
  public static <T> ConfigurablePidBuilder<T> configurable(Class<T> type) {
    return new ConfigProviderBuilder<T>(type);
  }
}
