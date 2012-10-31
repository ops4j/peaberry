/**
 * Copyright (C) 2010 Todor Boev
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
package org.ops4j.peaberry.activation.tests;

import static org.ops4j.pax.exam.CoreOptions.*;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.CompositeOption;

public class PeaberryConfiguration implements CompositeOption {
  public Option[] getOptions() {
    return options(
      mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.aopalliance").versionAsInProject(), 
      mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.javax-inject").versionAsInProject(), 
      mavenBundle().groupId("com.google.inject").artifactId("guice").versionAsInProject(),
      
      mavenBundle().groupId("org.ops4j").artifactId("peaberry").versionAsInProject(),
      mavenBundle().groupId("org.ops4j.peaberry.extensions").artifactId("peaberry.activation").versionAsInProject().noStart(),
      
      mavenBundle().groupId("org.ops4j.peaberry.extensions").artifactId("peaberry.activation.invocations").versionAsInProject());
  }
}
