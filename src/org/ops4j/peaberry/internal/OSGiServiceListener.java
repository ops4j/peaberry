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

package org.ops4j.peaberry.internal;

import static org.osgi.framework.ServiceEvent.UNREGISTERING;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ops4j.peaberry.AttributeFilter;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceListener
    implements ServiceListener {

  private final SortedSet<ServiceReference> services;

  public OSGiServiceListener() {
    services = new TreeSet<ServiceReference>(new BestServiceComparator());
  }

  public void serviceChanged(final ServiceEvent event) {
    final ServiceReference ref = event.getServiceReference();

    if (UNREGISTERING == event.getType()) {
      services.remove(ref);
    } else {
      services.add(ref);
    }
  }

  public Iterator<ServiceReference> iterator(final AttributeFilter filter) {
    return new Iterator<ServiceReference>() {

      public boolean hasNext() {
        // TODO Auto-generated method stub
        return false;
      }

      public ServiceReference next() {
        // TODO Auto-generated method stub
        return null;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  private Map<String, ?> attributes(final ServiceReference ref) {
    return new ServiceAttributes(ref);
  }
}
