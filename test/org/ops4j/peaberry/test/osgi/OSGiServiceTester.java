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

import static org.ops4j.peaberry.util.Attributes.attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.ServiceWatcher.Handle;

import com.google.inject.Inject;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public abstract class OSGiServiceTester {

  protected interface SimpleService {
    String check();
  }

  @Inject
  ServiceRegistry registry;

  static final Map<String, Handle<?>> handles =
      new HashMap<String, Handle<?>>();

  protected void enableService(final String name) {
    Properties properties = new Properties();
    properties.setProperty("name", name);
    properties.put(new Object(), "bogus");

    Handle<SimpleService> handle =
        registry.add((SimpleService) new SimpleService() {
          public String check() {
            if (handles.containsKey(name)) {
              return name;
            }

            throw new ServiceUnavailableException();
          }
        }, attributes(properties));

    handles.put(name, handle);

    String result = handle.get().check();
    assert name.equals(result) : "Expected " + name + ", got " + result;
  }

  protected void disableService(final String name) {
    handles.remove(name).remove();
  }

  protected void disableAllServices() {
    for (Handle<?> handle : handles.values()) {
      handle.remove();
    }
    handles.clear();
  }

  protected void checkService(SimpleService service, String name) {
    String result = service.check();
    assert name.equals(result) : "Expected " + name + ", got " + result;
  }

  protected void missingService(SimpleService service) {
    try {
      service.check();
      assert false : "Expected service exception";
    } catch (ServiceUnavailableException e) {}
  }

  protected void checkServices(Iterable<SimpleService> services,
      String... names) {
    int i = 0;
    for (SimpleService service : services) {
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

  protected void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {}
  }
}
