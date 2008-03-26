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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Target( {
    TYPE, FIELD, PARAMETER
})
@Retention(RUNTIME)
@BindingAnnotation
public @interface Service {

  /**
   * RFC-1960 (LDAP) filter
   */
  String value() default "";

  /**
   * Custom service API
   */
  Class<?>[] interfaces() default {};

  /**
   * Is this a mandatory service?
   */
  boolean mandatory() default false;

  /**
   * Service lookup policy
   */
  Policy policy() default Policy.DYNAMIC;

  /**
   * Available lookup policies
   */
  enum Policy {

    /**
     * Service tied to binding lifetime. Once a service is discovered it will
     * always be the same for a given binding, even if the service has stopped.
     */
    STATIC,

    /**
     * Service independent of binding lifetime, can come and go at any point.
     */
    DYNAMIC
  }
}
