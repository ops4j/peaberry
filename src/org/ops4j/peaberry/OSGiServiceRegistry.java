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

package org.ops4j.peaberry;

import java.util.Dictionary;
import com.google.inject.Module;

/**
 * Helper to manage registered OSGi services, a default implementation is bound
 * to this interface in the {@link Module} supplied with the Peaberry bundle.
 * 
 * <pre>
 *     {@literal @}Inject
 *     OSGiServiceRegistry registry;
 * 
 *     {@literal @}Inject
 *     {@literal @}OSGiServiceRegistration
 *     MyService basicService;
 * 
 *     public void suspendMyService() {
 *       registry.unregisterService(basicService);
 *     }
 * 
 *     public void resumeMyService() {
 *       registry.registerService(basicService);
 *     }
 * 
 *     public Dictionary changeMyServiceSettings(Dictionary newSettings) {
 *       Dictionary oldSettings = registry.getServiceProperties(basicService);
 *       registry.setServiceProperties(basicService, newSettings);
 *       return oldSettings;
 *     }
 * </pre>
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public interface OSGiServiceRegistry {

  /**
   * Register this OSGi service with the Service Registry
   * 
   * @param service injected {@literal @}OSGiServiceRegistration member
   */
  void registerService(Object service);

  /**
   * Unregister this OSGi service from the Service Registry
   * 
   * @param service injected {@literal @}OSGiServiceRegistration member
   */
  void unregisterService(Object service);

  /**
   * Is this OSGi service registered with the Service Registry?
   * 
   * @param service injected {@literal @}OSGiServiceRegistration member
   * @return true if the service is registered, otherwise false
   */
  boolean isServiceRegistered(Object service);

  /**
   * Is this OSGi service being used?
   * 
   * @param service injected {@literal @}OSGiServiceRegistration member
   * @return true if the service is being used, otherwise false
   */
  boolean isServiceInUse(Object service);

  /**
   * Retrieve the current properties for this OSGi service
   * 
   * @param service injected {@literal @}OSGiServiceRegistration member
   * @return dictionary of OSGi service properties
   */
  Dictionary<?, ?> getServiceProperties(Object service);

  /**
   * Update the registered properties for this OSGi service
   * 
   * @param service injected {@literal @}OSGiServiceRegistration member
   * @param properties dictionary of OSGi service properties
   */
  void setServiceProperties(Object service, Dictionary<?, ?> properties);
}
