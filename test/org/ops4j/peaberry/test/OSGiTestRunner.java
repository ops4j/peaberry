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

package org.ops4j.peaberry.test;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.getBundleModule;
import static org.ops4j.peaberry.test.GuiceObjectFactory.setBinder;
import static org.ops4j.peaberry.test.GuiceObjectFactory.setInjector;

import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.testng.IClass;
import org.testng.ISuite;
import org.testng.TestRunner;
import org.testng.xml.XmlTest;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class OSGiTestRunner
    extends TestRunner {

  private static final long serialVersionUID = 1L;

  private static volatile BundleContext bundleContext;

  public static BundleContext getBundleContext() {
    return bundleContext;
  }

  public OSGiTestRunner(ISuite suite, XmlTest test) {
    super(suite, test, false);
  }

  private Module testModule() {
    return new Module() {
      @SuppressWarnings("unchecked")
      public void configure(Binder binder) {
        setBinder(binder);
        for (IClass iClazz : getIClass()) {
          binder.bind(iClazz.getRealClass());
        }
      }
    };
  }

  @Override
  public void run() {

    Properties p = new Properties();
    p.setProperty("felix.cache.profile", "test");
    p.setProperty("felix.embedded.execution", "true");
    Felix felix = new Felix(p, null);

    try {

      felix.start();

      BundleContext bc = felix.getBundleContext();
      setInjector(createInjector(getBundleModule(bc), testModule()));

//      bc.installBundle("file:/home/stuart/Code/peaberry/build/dist/peaberry-snapshot.jar").start();

      super.run();

    } catch (BundleException e) {
      e.printStackTrace();
    } finally {
      try {
        felix.stop();
      } catch (BundleException e) {
        e.printStackTrace();
      }
    }
  }
}
