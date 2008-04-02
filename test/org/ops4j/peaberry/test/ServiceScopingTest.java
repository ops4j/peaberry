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

package org.ops4j.peaberry.test;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.ops4j.peaberry.Leased;
import org.ops4j.peaberry.Service;
import org.ops4j.peaberry.Static;
import org.osgi.service.log.LogService;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public class ServiceScopingTest
    implements Module {

  @Service
  @Leased(seconds = 10)
  @BindingAnnotation
  @Retention(RUNTIME)
  public @interface LeasedService {}

  @Service
  @Static
  @BindingAnnotation
  @Retention(RUNTIME)
  public @interface StaticService {}

  @Inject
  @Service
  LogService m_logService;

  @Inject
  @LeasedService
  LogService m_leasedLogService;

  @Inject
  @StaticService
  LogService m_staticLogService;

  public void configure(Binder binder) {
    binder.bind(ServiceScopingTest.class);
  }

  @Test
  public void testMe() {
    m_logService.log(LogService.LOG_INFO, "THIS");
    m_leasedLogService.log(LogService.LOG_INFO, "IS A");
    m_staticLogService.log(LogService.LOG_INFO, "TEST");
  }
}
