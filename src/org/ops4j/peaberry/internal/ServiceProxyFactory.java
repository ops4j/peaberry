/**
 * Copyright (C) 2008 Stuart McCulloch
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

package org.ops4j.peaberry.internal;

import org.ops4j.peaberry.ServiceRegistry;

import com.google.inject.cglib.proxy.Dispatcher;
import com.google.inject.cglib.proxy.Enhancer;
import com.google.inject.internal.GuiceCodeGen;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ServiceProxyFactory {

  private ServiceProxyFactory() {
    // don't allow instances of helper class
  }

  public static <T> T get(Class<T> type, final ServiceRegistry registry,
      final String query) {

    Enhancer proxy = GuiceCodeGen.getEnhancer(type);
    proxy.setCallback(new Dispatcher() {
      public Object loadObject()
          throws Exception {
        // check the registry on every call
        return registry.lookup(query).next();
      }
    });

    return type.cast(proxy.create());
  }
}
