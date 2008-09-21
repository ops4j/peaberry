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

import static org.osgi.framework.Constants.SERVICE_ID;
import static org.testng.Assert.assertEquals;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.testng.annotations.Test;

/**
 * Corner-case tests for custom service-ranking algorithm.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class BestServiceComparatorTests {

  @SuppressWarnings("unused")
  public void testBestServiceCompare() {
    // /CLOVER:OFF

    // service reference with no properties at all
    final ServiceReference empty = new ServiceReference() {

      public int compareTo(final Object reference) {
        return 0;
      }

      public Bundle getBundle() {
        return null;
      }

      public Object getProperty(final String key) {
        if (SERVICE_ID.equals(key)) {
          return 0L;
        }
        return null;
      }

      public String[] getPropertyKeys() {
        return null;
      }

      public Bundle[] getUsingBundles() {
        return null;
      }

      public boolean isAssignableTo(final Bundle bundle, final String className) {
        return false;
      }
    };

    // service reference with only a service ID
    final ServiceReference basic = new ServiceReference() {

      public int compareTo(final Object reference) {
        return 0;
      }

      public Bundle getBundle() {
        return null;
      }

      public Object getProperty(final String key) {
        if (SERVICE_ID.equals(key)) {
          return 1L;
        }
        return null;
      }

      public String[] getPropertyKeys() {
        return null;
      }

      public Bundle[] getUsingBundles() {
        return null;
      }

      public boolean isAssignableTo(final Bundle bundle, final String className) {
        return false;
      }
    };

    // /CLOVER:ON

    final BestServiceComparator comparator = new BestServiceComparator();

    assertEquals(comparator.compare(empty, empty), 0);
    assertEquals(comparator.compare(basic, empty), +1);
    assertEquals(comparator.compare(empty, basic), -1);
    assertEquals(comparator.compare(basic, basic), 0);
  }
}
