/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry.internal;

import com.google.inject.BindingFactory;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.spi.Dependency;

/**
 * Provide on-demand bindings for OSGi service registrations.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiServiceRegistrationBindingFactory
    implements BindingFactory<Object> {

  /**
   * {@inheritDoc}
   */
  public <T> boolean bind(Dependency<T> dependency,
      LinkedBindingBuilder<T> linkedBindingBuilder) {

    // TODO Auto-generated method stub
    System.out.println("AUTOBIND:" + dependency.getKey().getAnnotation());
    linkedBindingBuilder.toInstance((T) new java.util.ArrayList());
    return true;
  }
}
