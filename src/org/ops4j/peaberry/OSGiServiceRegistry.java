/**
 * Copyright (C) 2008 Stuart McCulloch
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.ops4j.peaberry;

import java.util.Dictionary;
import com.google.inject.Module;

/**
 * Helper to manage registered OSGi services, a default implementation is
 * bound to this interface in the {@link Module} supplied with the Peaberry
 * bundle.
 * 
 * <pre>
 *     {@literal @}Inject
 *     OSGiServiceRegistry registry;
 * 
 *     {@literal @}Inject
 *     {@literal @}OSGiService
 *     {@literal @}ImplementedBy(MyServiceImpl.class)
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
   * Retrieve the registered OSGi service implementation
   * 
   * @param member injected {@literal @}OSGiService member
   */
  <T> T getRegisteredService(Object member);

  /**
   * Register this OSGi service with the Service Registry
   * 
   * @param member injected {@literal @}OSGiService member
   */
  void registerService(Object member);

  /**
   * Unregister this OSGi service from the Service Registry
   * 
   * @param member injected {@literal @}OSGiService member
   */
  void unregisterService(Object member);

  /**
   * Is this OSGi service registered with the Service Registry?
   * 
   * @param member injected {@literal @}OSGiService member
   * @return true if the service is registered, otherwise false
   */
  boolean isServiceRegistered(Object member);

  /**
   * Is this OSGi service being used?
   * 
   * @param member injected {@literal @}OSGiService member
   * @return true if the service is being used, otherwise false
   */
  boolean isServiceInUse(Object member);

  /**
   * Retrieve the current properties for this OSGi service
   * 
   * @param member injected {@literal @}OSGiService member
   * @return dictionary of OSGi service properties
   */
  Dictionary<?, ?> getServiceProperties(Object member);

  /**
   * Update the registered properties for this OSGi service
   * 
   * @param member injected {@literal @}OSGiService member
   * @param properties dictionary of OSGi service properties
   */
  void setServiceProperties(Object member, Dictionary<?, ?> properties);
}
