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

import java.util.Map;

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher.Handle;
import org.ops4j.peaberry.builders.QualifiedRegistrationBuilder;
import org.ops4j.peaberry.builders.RegistrationProxyBuilder;
import org.ops4j.peaberry.builders.ScopedRegistrationBuilder;

import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class RegistrationBuilderImpl<T>
    implements QualifiedRegistrationBuilder<T> {

  public RegistrationBuilderImpl(final Key<? extends T> key) {
  // TODO Auto-generated constructor stub
  }

  public ScopedRegistrationBuilder<T> attributes(final Map<String, ?> attributes) {
    // TODO Auto-generated method stub
    return this;
  }

  public RegistrationProxyBuilder<T> registry(final ServiceRegistry registry) {
    // TODO Auto-generated method stub
    return this;
  }

  public Provider<Handle<T>> handle() {
    // TODO Auto-generated method stub
    return null;
  }
}
