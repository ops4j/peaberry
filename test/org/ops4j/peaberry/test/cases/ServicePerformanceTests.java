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

package org.ops4j.peaberry.test.cases;

import static com.google.inject.name.Names.named;
import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Decorators.sticky;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * Test service performance compared to direct method invocation.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test
public final class ServicePerformanceTests
    extends InjectableTestCase {

  @Override
  protected void configure() {

    // service uses same implementation
    bind(export(Example.class)).toProvider(registration(Key.get(ExampleImpl.class)).export());

    // standard injection: raw method invocation
    bind(Example.class).annotatedWith(named("Raw")).to(ExampleImpl.class);

    // service registry lookup: indirect method invocation via normal proxy
    bind(Example.class).annotatedWith(named("Service")).toProvider(service(Example.class).single());

    // service registry lookup: raw method invocation
    bind(Example.class).annotatedWith(named("Direct")).toProvider(
        service(Example.class).direct().single());

    // service registry lookup: indirect method invocation via sticky proxy
    bind(Example.class).annotatedWith(named("Sticky")).toProvider(
        service(Example.class).decoratedWith(sticky(null)).single());

    bind(Holder.class);
  }

  protected interface Example {
    double action(String name, double id);
  }

  static class ExampleImpl
      implements Example {
    public double action(final String name, final double id) {
      return id * name.hashCode() * Math.cosh(id);
    }
  }

  static class Holder {

    @Inject
    @Named("Raw")
    public Example raw;

    @Inject
    @Named("Service")
    public Example service;

    @Inject
    @Named("Direct")
    public Example direct;

    @Inject
    @Named("Sticky")
    public Example sticky;
  }

  public void testServiceLookupPerformance() {

    injector.getInstance(Key.get(export(Example.class)));
    final Holder holder = injector.getInstance(Holder.class);

    // warm-up...
    timeExample(holder.raw);
    timeExample(holder.service);
    timeExample(holder.direct);
    timeExample(holder.sticky);

    System.out.println();
    System.out.format("RAW INSTANCE   %8.2f ns / call\n", timeExample(holder.raw));
    System.out.format("SERVICE PROXY  %8.2f ns / call\n", timeExample(holder.service));
    System.out.format("DIRECT SERVICE %8.2f ns / call\n", timeExample(holder.direct));
    System.out.format("STICKY SERVICE %8.2f ns / call\n", timeExample(holder.sticky));
  }

  private static double timeExample(final Example example) {
    example.action("This is a test", 1.0);
    final long now = System.currentTimeMillis();
    for (double i = 0; i < 1; i += 0.000001) {
      example.action("This is a test", i);
    }
    return System.currentTimeMillis() - now;
  }
}
