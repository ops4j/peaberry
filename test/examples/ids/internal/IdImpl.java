/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"){}
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

package examples.ids.internal;

import examples.ids.Id;

/**
 * Trivial {@code Id} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class IdImpl
    implements Id {

  private final String id;

  public IdImpl(final String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return id;
  }

  // /CLOVER:OFF

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(final Object rhs) {
    if (rhs instanceof Id) {
      return toString().equals(rhs.toString());
    }
    return false;
  }

  // /CLOVER:ON
}
