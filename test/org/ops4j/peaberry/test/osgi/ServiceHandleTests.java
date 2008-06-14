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

import static java.util.Collections.singletonMap;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.ops4j.peaberry.ServiceUnavailableException;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.ServiceWatcher.Handle;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;

/**
 * Test service registration using {@link ServiceWatcher} interface.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
@Test(testName = "ServiceHandleTests", suiteName = "OSGi")
public class ServiceHandleTests
    extends OSGiServiceTester {

  protected interface WordService {
    String getWord();
  }

  protected static class WordServiceImplA
      implements WordService {

    public String getWord() {
      return "A";
    }
  }

  protected static class WordServiceImplB
      implements WordService {

    public String getWord() {
      return "B";
    }
  }

  @Inject
  @Service(attributes = "word=A")
  Handle<WordService> producerA;

  @Inject
  @Service(attributes = "word=B")
  Handle<WordService> producerB;

  @Inject
  @Service(filter = "word=A")
  WordService consumerA;

  @Inject
  @Service(filter = "word=B")
  WordService consumerB;

  @Test(enabled = false)
  public static void setup(final Binder binder, final BundleContext bundleContext) {

    // standard OSGi service injection module
    binder.install(osgiModule(bundleContext));

    // bind service implementations for registration
    binder.bind(WordService.class).annotatedWith(service().attributes("word=A").build()).to(WordServiceImplA.class);
    binder.bind(WordService.class).annotatedWith(service().attributes("word=B").build()).to(WordServiceImplB.class);
  }

  private void checkWord(final String word, final String result) {
    assert word.equals(result) : "Expected " + word + ", got " + result;
  }

  public void testWiring() {

    checkWord("A", consumerA.getWord());
    checkWord("B", consumerB.getWord());

    checkWord("A", producerA.get().getWord());
    checkWord("B", producerB.get().getWord());

    // this should change the dynamic bindings
    producerA.modify(singletonMap("word", "B"));
    producerB.modify(singletonMap("word", "A"));

    checkWord("B", consumerA.getWord());
    checkWord("A", consumerB.getWord());

    producerA.remove();
    producerB.remove();

    try {
      consumerA.getWord();
      assert false : "No service expected";
    } catch (final ServiceUnavailableException e) {}

    try {
      consumerB.getWord();
      assert false : "No service expected";
    } catch (final ServiceUnavailableException e) {}
  }
}
