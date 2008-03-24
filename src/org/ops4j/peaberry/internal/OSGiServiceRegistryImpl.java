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

import java.util.Dictionary;
import org.ops4j.peaberry.OSGiServiceRegistry;

/**
 * Provide basic API for dealing with the OSGi Service Registry.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiServiceRegistryImpl
    implements OSGiServiceRegistry {

  /**
   * {@inheritDoc}
   */
  public Dictionary<?, ?> getServiceProperties(Object service) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isServiceRegistered(Object service) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isServiceInUse(Object service) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void registerService(Object service) {
    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}
   */
  public void setServiceProperties(Object service, Dictionary<?, ?> properties) {
    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}
   */
  public void unregisterService(Object service) {
    // TODO Auto-generated method stub
  }
}
