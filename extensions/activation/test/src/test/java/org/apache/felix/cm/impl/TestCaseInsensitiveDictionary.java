package org.apache.felix.cm.impl;

import org.ops4j.peaberry.activation.internal.ConfigurationMapTest;

/**
 * Extend package protected {@link CaseInsensitiveDictionary} to be able to use it in {@link ConfigurationMapTest}.
 *
 * @since 1.4
 */
public class TestCaseInsensitiveDictionary
    extends CaseInsensitiveDictionary
{
  public TestCaseInsensitiveDictionary() {}
}
