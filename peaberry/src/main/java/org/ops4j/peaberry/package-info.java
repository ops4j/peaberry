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

/**
 * <i>peaberry</i> - Dynamic services for <a target="_blank"
 * href="http://code.google.com/p/google-guice/">Google-Guice</a>.
 *
 * <p>The principal members of this package are:
 *
 * <dl>
 *
 * <dt>{@link org.ops4j.peaberry.Peaberry}
 * <dd>The builder that can assemble providers to import or export dynamic services.
 *
 * <dt>{@link org.ops4j.peaberry.ServiceUnavailableException}
 * <dd>The exception thrown when you attempt to use a service that is not available.
 *
 * <dt>{@link org.ops4j.peaberry.ServiceRegistry}
 * <dd>The interface you can implement in order to plug-in other service frameworks.
 *
 * <dt>{@link org.ops4j.peaberry.ServiceWatcher}
 * <dd>The interface you can implement in order to watch services coming and going.
 *
 * </dl>
 */
package org.ops4j.peaberry;

