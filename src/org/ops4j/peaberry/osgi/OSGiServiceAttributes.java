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

package org.ops4j.peaberry.osgi;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

import org.osgi.framework.ServiceReference;

/**
 * Service attributes adapter backed by an OSGi {@code ServiceReference}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class OSGiServiceAttributes
    extends AbstractMap<String, Object> {

  final ServiceReference ref;

  public OSGiServiceAttributes(final ServiceReference ref) {
    this.ref = ref;
  }

  @Override
  public Object get(final Object key) {
    return ref.getProperty((String) key);
  }

  // can safely cache entry set, as it has no state
  private volatile Set<Entry<String, Object>> entrySet;

  @Override
  public Set<Entry<String, Object>> entrySet() {
    if (null == entrySet) {
      entrySet = new AbstractSet<Entry<String, Object>>() {

        @Override
        public Iterator<Entry<String, Object>> iterator() {
          final String[] keys = ref.getPropertyKeys();
          return new Iterator<Entry<String, Object>>() {
            private int i = 0;

            public boolean hasNext() {
              return i < keys.length;
            }

            public Entry<String, Object> next() {
              return new ServiceAttribute(keys[i]);
            }

            public void remove() {
              throw new UnsupportedOperationException();
            }
          };
        }

        @Override
        public int size() {
          return ref.getPropertyKeys().length;
        }
      };
    }
    return entrySet;
  }

  final class ServiceAttribute
      implements Entry<String, Object> {

    private final String key;

    public ServiceAttribute(final String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public Object getValue() {
      return ref.getProperty(key);
    }

    public Object setValue(final Object value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(final Object rhs) {
      if (rhs instanceof Entry) {
        final Object rhsKey = ((Entry<?, ?>) rhs).getKey();
        return null == key ? null == rhsKey : key.equals(rhsKey);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return (null == key ? 0 : key.hashCode());
    }
  }
}
