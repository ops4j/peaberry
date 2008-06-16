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

package org.ops4j.peaberry;

/**
 * Exception thrown when a requested {@link Service} is not available.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
public final class ServiceUnavailableException
    extends ServiceException {

  private static final long serialVersionUID = 9207041522801931804L;

  /**
   * Constructs a {@link ServiceUnavailableException} with no message or cause.
   */
  public ServiceUnavailableException() {
    super();
  }

  /**
   * Constructs a {@link ServiceUnavailableException} with a specific message.
   * 
   * @param message detailed message
   */
  public ServiceUnavailableException(final String message) {
    super(message);
  }

  /**
   * Constructs a {@link ServiceUnavailableException} with a specific cause.
   * 
   * @param cause underlying cause
   */
  public ServiceUnavailableException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a {@link ServiceUnavailableException} with message and cause.
   * 
   * @param message detailed message
   * @param cause underlying cause
   */
  public ServiceUnavailableException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
