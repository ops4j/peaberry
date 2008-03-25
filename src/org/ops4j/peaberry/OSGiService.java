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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.google.inject.BindingAnnotation;
import com.google.inject.ImplementedBy;
import com.google.inject.Injector;

/**
 * Annotates members of your implementation class (constructors, methods or
 * fields) where the {@link Injector} should inject either an OSGi service
 * proxy, or a {@link ServiceTrackerCustomizer} implementation backed by a
 * {@link ServiceTracker}.
 * 
 * Service implementations can be registered with the OSGi framework by using
 * the {@link OSGiService} annotation on a member with an existing binding,
 * such as {@link ImplementedBy}.
 * 
 * Service implementations can be managed using {@link OSGiServiceRegistry}.
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService
 *     MyService zeroToOne;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService
 *     Iterable&lt;MyService&gt; zeroToMany;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(mandatory = true)
 *     MyService oneToOne;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(mandatory = true)
 *     Iterable&lt;MyService&gt; oneToMany;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(policy = STATIC)
 *     MyService fixedAtStartup;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(&quot;(lang=fr)&quot;)
 *     MyService ldapFiltered;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(&quot;(lang=$user.language)&quot;)
 *     MyService ldapFilteredUsingProperty;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(&quot;(lang=%User-Language)&quot;)
 *     MyService ldapFilteredUsingBundleHeader;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(interfaces = {MySpecialisedService.class})
 *     MyService customInterface;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(interfaces = {MyService.class})
 *     ServiceTracker basicTracker;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(interfaces = {MyService.class})
 *     {@literal @}ImplementedBy(MyServiceTrackerCustomizer.class)
 *     ServiceTracker customTracker;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(interfaces = {MyService.class})
 *     {@literal @}ImplementedBy(MyServiceImpl.class)
 *     ServiceRegistration serviceRegistration;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(interfaces = {MyService.class}, policy = LAZY)
 *     {@literal @}ImplementedBy(MyServiceImpl.class)
 *     ServiceRegistration lazyServiceRegistration;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService
 *     {@literal @}ImplementedBy(MyServiceImpl.class)
 *     MyService serviceRegistrationAndLookup;
 * </pre>
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Target( {
    TYPE, FIELD, PARAMETER
})
@Retention(RUNTIME)
@BindingAnnotation
public @interface OSGiService {

  /**
   * RFC-1960 (LDAP) filter
   */
  String value() default "";

  /**
   * Customized service API
   */
  Class<?>[] interfaces() default {};

  /**
   * Is this a mandatory service?
   */
  boolean mandatory() default false;

  /**
   * Selected service policy
   */
  Policy policy() default Policy.DYNAMIC;

  /**
   * Available service policies
   */
  enum Policy {
    STATIC, DYNAMIC, LAZY
  }
}
