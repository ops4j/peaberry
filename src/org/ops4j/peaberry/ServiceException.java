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
 * General purpose service exception.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class ServiceException
    extends RuntimeException {

  private static final long serialVersionUID = -1744260261446638374L;

  /**
   * Constructs a {@code ServiceException} with no message or cause.
   */
  public ServiceException() {
    super();
  }

  /**
   * Constructs a {@code ServiceException} with a specific message.
   * 
   * @param message detailed message
   */
  public ServiceException(final String message) {
    super(message);
  }

  /**
   * Constructs a {@code ServiceException} with a specific cause.
   * 
   * @param cause underlying cause
   */
  public ServiceException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a {@code ServiceException} with message and cause.
   * 
   * @param message detailed message
   * @param cause underlying cause
   */
  public ServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
