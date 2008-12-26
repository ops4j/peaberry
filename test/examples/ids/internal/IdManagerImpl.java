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

import static org.ops4j.peaberry.Peaberry.service;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceRegistry;

import com.google.inject.Inject;

import examples.ids.Id;
import examples.ids.IdManager;

/**
 * {@link IdManager} implementation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class IdManagerImpl
    implements IdManager {

  private final Set<Export<Id>> exportedIds;
  private final ServiceRegistry registry;

  @Inject
  public IdManagerImpl(final ServiceRegistry registry) {
    exportedIds = new HashSet<Export<Id>>();
    this.registry = registry;
  }

  public void add(final Map<String, ?> attributes, final String... ids) {
    for (final String id : ids) {
      assertNull(find(id));
      exportedIds.add(export(new IdImpl(id), new HashMap<String, Object>(attributes)));
      assertNotNull(find(id));
    }
  }

  public void remove(final String... ids) {
    for (final String id : ids) {
      final Export<Id> exportedId = find(id);
      assertNotNull(exportedId);
      exportedIds.remove(exportedId);
      exportedId.unput();
      assertNull(find(id));
    }
  }

  public void clear() {
    for (final Export<Id> exportedId : exportedIds) {
      exportedId.unput();
    }
    exportedIds.clear();
  }

  private Export<Id> export(final Id id, final Map<String, Object> attributes) {
    attributes.put("id", id.toString());

    return service(id).attributes(attributes).in(registry).export().get();
  }

  private Export<Id> find(final String id) {
    for (final Export<Id> exportedId : exportedIds) {
      if (id.equals(exportedId.get().toString())) {
        return exportedId;
      }
    }
    return null;
  }
}
