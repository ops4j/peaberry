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

package org.ops4j.peaberry.test.osgi;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Inject;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public abstract class OSGiServiceTester {

  protected interface TestService {
    String check();
  }

  @Inject
  BundleContext bundleContext;

  static final Map<String, ServiceRegistration> registrations =
      new HashMap<String, ServiceRegistration>();

  protected void enableService(final String name) {
    Properties properties = new Properties();
    properties.setProperty("name", name);

    ServiceRegistration registration =
        bundleContext.registerService(TestService.class.getName(),
            new TestService() {
              public String check() {
                if (registrations.containsKey(name)) {
                  return name;
                } else {
                  throw new RuntimeException("Missing Service");
                }
              }
            }, properties);

    registrations.put(name, registration);
  }

  protected void disableService(final String name) {
    registrations.remove(name).unregister();
  }

  protected void disableAllServices() {
    for (ServiceRegistration registration : registrations.values()) {
      registration.unregister();
    }
    registrations.clear();
  }

  protected void checkService(TestService service, String name) {
    String result = service.check();
    assert name.equals(result) : "Expected " + name + ", got " + result;
  }

  protected void missingService(TestService service) {
    try {
      service.check();
      assert false : "Expected service exception";
    } catch (Exception e) {}
  }

  protected void checkServices(Iterable<TestService> services, String... names) {
    int i = 0;
    for (TestService service : services) {
      assert names.length > i : "More services than expected";
      try {
        checkService(service, names[i]);
      } catch (Exception e) {
        if (!names[i].equals("!")) {
          throw new RuntimeException(e);
        }
      }
      i++;
    }
    assert names.length == i : "Less services than expected";
  }

  protected static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {}
  }
}
