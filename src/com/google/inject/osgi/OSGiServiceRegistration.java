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
import com.google.inject.BindingAnnotation;
import com.google.inject.Injector;

/**
 * Annotates members of your implementation class (constructors, methods and
 * fields) where the {@link Injector} should take the injected implementation
 * and register it with the framework as an OSGi service. Registered services
 * can be managed by using the {@link OSGiServiceRegistry} helper.
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiServiceRegistration
 *     MyService basicService;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiServiceRegistration(&quot;lang=fr,qos=3&quot;)
 *     MyService ldapFiltered;
 * </pre>
 * 
 * <pre>
 *     {@literal @}Inject
 *     {@literal @}OSGiServiceRegistration(
 *       interfaces = {MyService.class, MySpecialisedService.class}
 *     )
 *     MyService customInterface;
 * </pre>
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Target( {
  TYPE, FIELD, PARAMETER
})
@Retention(RUNTIME)
@BindingAnnotation
public @interface OSGiServiceRegistration {

  /**
   * RFC-1960 (LDAP) filter
   */
  String value() default "";

  /**
   * Customized service API
   */
  Class<?>[] interfaces() default {};

  /**
   * Service starts as registered?
   */
  boolean registered() default true;
}
