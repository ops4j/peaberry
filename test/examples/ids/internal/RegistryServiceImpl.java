/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"){}
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

package examples.ids.internal;

import static org.osgi.framework.Constants.SERVICE_RANKING;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import examples.ids.IdService;
import examples.ids.RegistryService;

/**
 * {@code RegistryService} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class RegistryServiceImpl
    implements RegistryService {

  private final Map<String, ServiceRegistration> idMap;
  private final BundleContext ctx;

  public RegistryServiceImpl(final BundleContext ctx) {
    idMap = new HashMap<String, ServiceRegistration>();
    this.ctx = ctx;
  }

  public void register(final int ranking, final String... ids) {
    for (final String id : ids) {
      assertNull(idMap.get(id));

      final Properties props = new Properties();
      props.put(SERVICE_RANKING, ranking);
      props.setProperty("id", id);

      idMap.put(id, ctx.registerService(IdService.class.getName(), new IdServiceImpl(id), props));
    }
  }

  public void unregister(final String... ids) {
    for (final String id : ids) {
      assertNotNull(idMap.get(id));
      idMap.remove(id).unregister();
    }
  }

  public void reset() {
    unregister(idMap.keySet().toArray(new String[idMap.size()]));
  }
}
