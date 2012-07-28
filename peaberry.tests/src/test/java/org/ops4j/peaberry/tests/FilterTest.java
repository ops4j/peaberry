/**
 * Copyright (C) 2012 Stuart McCulloch
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

package org.ops4j.peaberry.tests;

import java.util.Collections;

import junit.framework.TestCase;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.util.Filters;

public class FilterTest
    extends TestCase
{
    public void testLDAPFilter()
    {
        AttributeFilter ldap = Filters.ldap( "(example=test)" );

        assertTrue( ldap.matches( Collections.singletonMap( "example", "test" ) ) );
    }
}
