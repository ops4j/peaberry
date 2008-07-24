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

package org.ops4j.peaberry.test.osgi;

import static com.google.inject.name.Names.named;
import static java.util.Collections.singletonMap;
import static org.ops4j.peaberry.Peaberry.registration;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.names;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.ServiceScope;
import org.ops4j.peaberry.ServiceUnavailableException;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * Test service registration using {@link ServiceScope} interface.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
@Test(testName = "ServiceExportTests", suiteName = "OSGi")
public final class ServiceExportTests {

  @Test(enabled = false)
  public static void configure(final Binder binder) {

    binder.bind(export(HelloService.class)).annotatedWith(named("A")).toProvider(
        registration(Key.get(HelloServiceLong.class)).attributes(names("style=long")).export());

    binder.bind(export(HelloService.class)).annotatedWith(named("B")).toProvider(
        registration(Key.get(HelloServiceShort.class)).attributes(names("style=short")).export());

    binder.bind(HelloService.class).annotatedWith(named("A")).toProvider(
        service(HelloService.class).filter("style=long").single());

    binder.bind(HelloService.class).annotatedWith(named("B")).toProvider(
        service(HelloService.class).filter("style=short").single());
  }

  protected interface HelloService {
    String say(String name);
  }

  static class HelloServiceLong
      implements HelloService {

    public String say(final String name) {
      return "Hello, " + name;
    }
  }

  static class HelloServiceShort
      implements HelloService {

    public String say(final String name) {
      return "Hi, " + name;
    }
  }

  @Inject
  @Named("A")
  Export<HelloService> producerA;

  @Inject
  @Named("B")
  Export<HelloService> producerB;

  @Inject
  @Named("A")
  HelloService consumerA;

  @Inject
  @Named("B")
  HelloService consumerB;

  void checkResponse(final String expected, final String result) {
    assert expected.equals(result) : "Expected " + expected + ", got " + result;
  }

  public void testWiring() {

    checkResponse("Hello, A", consumerA.say("A"));
    checkResponse("Hi, B", consumerB.say("B"));

    checkResponse("Hello, A", producerA.get().say("A"));
    checkResponse("Hi, B", producerB.get().say("B"));

    // this should switch the client dynamic bindings
    producerA.modify(singletonMap("style", "short"));
    producerB.modify(singletonMap("style", "long"));

    checkResponse("Hi, A", consumerA.say("A"));
    checkResponse("Hello, B", consumerB.say("B"));

    checkResponse("Hello, A", producerA.get().say("A"));
    checkResponse("Hi, B", producerB.get().say("B"));

    producerA.remove();
    producerB.remove();

    try {
      consumerA.say("A");
      assert false : "No service expected";
    } catch (final ServiceUnavailableException e) {}

    try {
      consumerB.say("B");
      assert false : "No service expected";
    } catch (final ServiceUnavailableException e) {}
  }
}
