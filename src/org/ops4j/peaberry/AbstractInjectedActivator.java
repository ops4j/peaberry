/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry;

import java.util.Collections;
import java.util.List;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Provide a base activator that automatically injects members on startup.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public abstract class AbstractInjectedActivator
  implements BundleActivator {

  private volatile Injector m_injector;

  /**
   * {@inheritDoc}
   */
  public final void start(BundleContext bc)
    throws Exception {

    m_injector = Peaberry.getOSGiInjector(bc, getModules());
    m_injector.injectMembers(this);

    start();
  }

  /**
   * {@inheritDoc}
   */
  public final void stop(BundleContext bc)
    throws Exception {

    stop();

    m_injector = null;
  }

  /**
   * Customized start method
   * 
   * @throws Exception
   */
  protected abstract void start()
    throws Exception;

  /**
   * Customized stop method
   * 
   * @throws Exception
   */
  protected abstract void stop()
    throws Exception;

  /**
   * @return customized list of Guice modules
   */
  protected List<? extends Module> getModules() {
    return Collections.emptyList();
  }

  /**
   * @return current dependency injector
   */
  public final Injector getInjector() {
    return m_injector;
  }
}
