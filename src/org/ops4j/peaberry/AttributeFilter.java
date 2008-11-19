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
 * Basic matching filter for service attributes.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public interface AttributeFilter {

  /**
   * Tests whether or not this filter matches the given service attributes.
   * 
   * @param attributes service attributes
   * 
   * @return true if the attributes were matched by the filter, otherwise false
   */
  boolean matches(Map<String, ?> attributes);
}
