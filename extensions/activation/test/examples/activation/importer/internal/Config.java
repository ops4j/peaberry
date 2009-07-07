/**
 * Copyright (C) 2009 Todor Boev
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

package examples.activation.importer.internal;

import org.ops4j.peaberry.Peaberry;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import examples.activation.hello.Hello;

/**
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 */
public class Config
    extends AbstractModule {

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    bind(Hello.class).toProvider(Peaberry.service(Hello.class).single());
    bind(Greeter.class).in(Singleton.class);
  }
}
