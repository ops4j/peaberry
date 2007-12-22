/**
 * Copyright (C) 2007 Stuart McCulloch
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

package com.google.inject.osgi;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import com.google.inject.Injector;

/**
 * Annotates members of your implementation class (constructors, methods and
 * fields) where the {@link Injector} should either inject an OSGi service
 * proxy, or a {@link ServiceTrackerCustomizer} implementation, backed by a
 * {@link ServiceTracker}.
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
 *     {@literal @}OSGiService(dynamic = false)
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
 *     {@literal @}OSGiService(interfaces = {MySpecialisedService.class})
 *     MyService customInterface;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiService(interfaces = MyService.class)
 *     {@literal @}ImplementedBy(MyServiceTrackerCustomizer.class)
 *     ServiceTrackerCustomizer listener;
 * </pre>
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Target( {
  TYPE, FIELD, PARAMETER
})
@Retention(RUNTIME)
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
   * Is this a dynamic service?
   */
  boolean dynamic() default true;
}
