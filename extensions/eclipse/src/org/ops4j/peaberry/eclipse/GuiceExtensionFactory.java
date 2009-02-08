/**
 * Copyright (C) 2009 Stuart McCulloch
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

package org.ops4j.peaberry.eclipse;

import static java.util.logging.Level.WARNING;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.OK;
import static org.osgi.framework.Bundle.ACTIVE;
import static org.osgi.framework.Bundle.STARTING;

import java.util.logging.Logger;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class GuiceExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory {

  private static final Logger LOGGER = Logger.getLogger(GuiceExtensionFactory.class.getName());

  private Class<?> clazz;
  private String pluginId;
  private CoreException oops;

  public void setInitializationData(final IConfigurationElement config, final String name,
      final Object data) {

    final Bundle bundle = ContributorFactoryOSGi.resolve(config.getContributor());
    if ((bundle.getState() & (STARTING | ACTIVE)) == 0) {
      try {
        bundle.start();
      } catch (final BundleException e) {
        LOGGER.log(WARNING, "Exception starting bundle", e);
      }
    }

    pluginId = bundle.getSymbolicName();

    try {
      clazz = bundle.loadClass(getClazzName(config, data));
    } catch (ClassNotFoundException e) {
      oops = coreException(e);
    }
  }

  public Object create()
      throws CoreException {

    if (oops != null) {
      throw oops;
    }

    try {
      return clazz.newInstance(); // TODO: use Guice injector
    } catch (InstantiationException e) {
      throw coreException(e);
    } catch (IllegalAccessException e) {
      throw coreException(e);
    }
  }

  private CoreException coreException(final Exception e) {
    return new CoreException(new Status(ERROR, pluginId, OK, e.getMessage(), e));
  }

  private static String getClazzName(final IConfigurationElement config, final Object data) {
    final String tag = null == data ? "" : data.toString();
    if (tag.contains(".")) {
      return tag;
    }

    return config.getAttribute(tag.length() == 0 ? "id" : tag);
  }
}
