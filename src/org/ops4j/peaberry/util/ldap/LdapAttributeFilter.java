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

package org.ops4j.peaberry.util.ldap;

import java.io.StringReader;
import java.util.Map;

import org.apache.felix.framework.util.ldap.EvaluationException;
import org.apache.felix.framework.util.ldap.Evaluator;
import org.apache.felix.framework.util.ldap.LdapLexer;
import org.apache.felix.framework.util.ldap.Mapper;
import org.apache.felix.framework.util.ldap.Parser;
import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.ServiceException;

/**
 * LDAP attribute filter, uses utility code from Apache Felix.
 * 
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class LdapAttributeFilter
    implements AttributeFilter {

  private final Object[] program;
  private volatile String filter;

  public LdapAttributeFilter(final String filter) {
    final Parser parser = new Parser(new LdapLexer(new StringReader(filter)));

    try {
      parser.start();
    } catch (final Exception e) {
      throw new IllegalArgumentException("Bad LDAP filter: " + filter, e);
    }

    program = parser.getProgram();

    if (null == program || program.length == 0) {
      throw new IllegalArgumentException("Bad LDAP filter: " + filter);
    }
  }

  public boolean matches(final Map<String, ?> attributes) {
    final Mapper mapper = new Mapper() {
      public Object lookup(final String key) {
        return attributes.get(key);
      }
    };

    try {
      return new Evaluator(program).evaluate(mapper);
    } catch (final EvaluationException e) {
      throw new ServiceException("Problem evaluating filter: " + this, e);
    }
  }

  @Override
  public String toString() {
    if (null == filter) {
      filter = new Evaluator(program).toStringInfix();
    }
    return filter;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof LdapAttributeFilter) {
      return toString().equals(obj.toString());
    }
    return false;
  }
}
