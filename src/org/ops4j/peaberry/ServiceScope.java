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

package org.ops4j.peaberry;

import java.util.Map;

/**
 * A write-only collection of exported services with associated attributes.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface ServiceScope {

  /**
   * Export the given service to this scope.
   * 
   * @param service service instance
   * @param attributes service attributes
   * 
   * @return exported service handle, null if this scope is not interested
   */
  <S, T extends S> Export<S> export(T service, Map<String, ?> attributes);
}
