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

import static org.ops4j.peaberry.util.Attributes.properties;
import static org.osgi.framework.Constants.SERVICE_RANKING;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceUnavailableException;

import com.google.inject.Inject;

/**
 * Helper class that registers simple unique services on-demand.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public abstract class OSGiServiceTester {

  protected interface SimpleService {
    String check();
  }

  @Inject
  ServiceRegistry registry;

  static final Map<String, Export<?>> handles = new HashMap<String, Export<?>>();

  protected final void enableService(final String name) {
    enableService(name, 0);
  }

  protected final void enableService(final String name, final int ranking) {

    final Properties properties = new Properties();
    properties.put(SERVICE_RANKING, ranking);
    properties.setProperty("name", name);

    final Export<SimpleService> handle = registry.export((SimpleService) new SimpleService() {
      public String check() {
        // check service is still valid
        if (handles.containsKey(name)) {
          return name;
        }
        throw new ServiceUnavailableException();
      }
    }, properties(properties));

    handles.put(name, handle);

    // check the service handle works
    final String result = handle.get().check();

    assert name.equals(result) : "Expected " + name + ", got " + result;
  }

  protected final void disableService(final String name) {
    handles.remove(name).remove();
  }

  protected final void disableAllServices() {
    for (final Export<?> handle : handles.values()) {
      handle.remove();
    }
    handles.clear();
  }

  protected final void checkService(final SimpleService service, final String name) {
    final String result = service.check();
    assert name.equals(result) : "Expected " + name + ", got " + result;
  }

  protected final void missingService(final SimpleService service) {
    try {
      service.check();
      assert false : "Expected service exception";
    } catch (final ServiceUnavailableException e) {}
  }

  protected final void checkServices(final Iterable<SimpleService> services, final String... names) {
    int i = 0;
    for (final SimpleService service : services) {
      assert names.length > i : "More services than expected";
      try {
        checkService(service, names[i]);
      } catch (final Exception e) {
        // allow testing for invalid entries
        if (!names[i].equals("!")) {
          throw new RuntimeException(e);
        }
      }
      i++;
    }
    assert names.length == i : "Less services than expected";
  }
}
