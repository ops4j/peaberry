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

import org.ops4j.peaberry.ServiceWatcher.Handle;

import com.google.inject.Provider;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public interface ServiceHandleBuilder<T> {

  /**
   * LDAP attributes, a sequence of "name=value" strings
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC-2253</a>
   */
  ServiceHandleBuilder<T> attributes(String... attributes);

  /**
   * Custom service API
   */
  ServiceHandleBuilder<T> interfaces(Class<?>... interfaces);

  /**
   * 
   */
  ServiceHandleBuilder<T> registry(ServiceRegistry registry);

  /**
   * 
   */
  Provider<Handle<T>> handle();
}
