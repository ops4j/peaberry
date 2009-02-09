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

import static java.util.Collections.singletonMap;

import org.ops4j.peaberry.ServiceException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * Custom {@link Scope} that provides singletons per {@link BundleContext}.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class BundleScopeImpl
    implements Scope {

  // attribute used to select bundle-scoped services
  private static final String BUNDLE_ID = "bundle.id";

  final BundleContext bundleContext;

  final long bundleId;
  final String filter;

  BundleScopeImpl(final BundleContext bundleContext) {
    this.bundleContext = bundleContext;

    // filter services to those registered by this context
    bundleId = bundleContext.getBundle().getBundleId();
    filter = '(' + BUNDLE_ID + '=' + bundleId + ')';
  }

  public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
    final String clazzName = key.getTypeLiteral().getRawType().getName();

    return new Provider<T>() {

      private volatile T instance; // cache for repeated requests

      @SuppressWarnings("unchecked")
      public T get() {

        if (null == instance) {
          synchronized (bundleContext) {
            if (null == instance) {
              final ServiceReference[] refs;

              try {
                // see if the context has an instance for this binding class
                refs = bundleContext.getServiceReferences(clazzName, filter);
              } catch (final InvalidSyntaxException e) {
                throw new ServiceException(e); // this should never happen!
              }

              if (refs != null && refs.length > 0) {
                // retrieve the existing instance from the registry
                instance = (T) bundleContext.getService(refs[0]);
              } else {
                instance = creator.get();

                // register the brand-new instance with the registry
                bundleContext.registerService(clazzName, instance,
                    new AttributeDictionary(singletonMap(BUNDLE_ID, bundleId)));
              }
            }
          }
        }

        return instance;
      }

      @Override
      public String toString() {
        return String.format("%s[BundleScope [%s]]", creator, bundleContext.getBundle());
      }
    };
  }

  @Override
  public String toString() {
    return String.format("BundleScope[%s]", bundleContext.getBundle());
  }
}
