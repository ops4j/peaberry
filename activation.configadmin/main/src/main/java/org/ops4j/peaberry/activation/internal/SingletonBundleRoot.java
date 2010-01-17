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
package org.ops4j.peaberry.activation.internal;

import static org.ops4j.peaberry.activation.internal.Reflection.*;

import java.lang.reflect.Method;
import java.util.List;

import com.google.inject.Key;

public class SingletonBundleRoot
    extends InstanceBundleRoot<Object> {

  private final List<Method> start;
  private final List<Method> stop;

  public SingletonBundleRoot(final Key<Object> key, final List<Method> start,
      final List<Method> stop) {
    
    super(key);

    this.start = start;
    this.stop = stop;
  }

  @Override
  protected void activate(final Object root) {
    for (final Method meth : start) {
      invoke(root, meth);
    }
  }

  @Override
  protected void deactivate(final Object root) {
    for (final Method meth : stop) {
      invoke(root, meth);
    }
  }
}