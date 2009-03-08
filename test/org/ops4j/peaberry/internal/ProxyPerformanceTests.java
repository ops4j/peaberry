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

import static java.util.Collections.singletonList;
import static org.ops4j.peaberry.internal.ImportProxyClassLoader.getProxyConstructor;
import static org.ops4j.peaberry.internal.ServiceProxyFactory.serviceProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.ops4j.peaberry.Import;
import org.testng.annotations.Test;

/**
 * Test proxy performance compared to direct method invocation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(sequential = true)
public final class ProxyPerformanceTests {

  protected interface Example {
    double action(String name, double id);
  }

  static class ExampleImpl
      implements Example {
    public double action(final String name, final double id) {
      return Math.exp(id * name.hashCode() * Math.cosh(id));
    }
  }

  public void testProxyPerformance() {

    final Example rawInstance = new ExampleImpl();

    final Example jdkProxy =
        (Example) Proxy.newProxyInstance(getClass().getClassLoader(),
            new Class<?>[]{Example.class}, new InvocationHandler() {
              public Object invoke(final Object proxy, final Method method, final Object[] args)
                  throws Throwable {
                return method.invoke(rawInstance, args);
              }
            });

    final Import<Example> staticImport = new StaticImport<Example>(rawInstance, null);

    final Example normalGlu;
    try {
      normalGlu = getProxyConstructor(Example.class).newInstance(staticImport);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }

    final Example cncrntGlu = serviceProxy(Example.class, singletonList(staticImport), null);

    // warm-up...
    time(rawInstance);
    time(rawInstance);
    time(rawInstance);

    System.out.println("Proxy overhead\n");
    final double baseline = time(rawInstance);

    benchmark("JDK PROXY ", baseline, time(jdkProxy));
    benchmark("NORMAL GLU", baseline, time(normalGlu));
    benchmark("CNCRNT GLU", baseline, time(cncrntGlu));

    System.out.println();
  }

  private static void benchmark(final String message, final double baseline, final double time) {
    System.out.format("%s %8.2f ns/call\n", message, time - baseline);
  }

  private static double time(final Example example) {
    System.gc();
    final long now = System.nanoTime();
    for (double i = 0; i < 1; i += 0.000001) {
      example.action("This is a test", i);
    }
    return (System.nanoTime() - now) / 1000000;
  }
}
