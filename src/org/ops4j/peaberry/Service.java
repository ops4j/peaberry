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
 * Specification for a dynamic service binding.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Target( {
    TYPE, FIELD, PARAMETER
})
@Retention(RUNTIME)
@BindingAnnotation
public @interface Service {

  /**
   * LDAP attributes, a sequence of name=value strings
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC-2253</a>
   */
  String[] attributes() default {};

  /**
   * LDAP filter
   * 
   * @see <a href="http://www.ietf.org/rfc/rfc1960.txt">RFC-1960</a>
   */
  String filter() default "";

  /**
   * Custom service API
   */
  Class<?>[] interfaces() default {};

  public @interface Seconds {
    int value();
  }

  static int FOREVER = -1;

  /**
   * Service lease period
   */
  Seconds lease() default @Seconds(0);
}
