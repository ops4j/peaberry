package org.ops4j.peaberry.activation.internal;

import java.util.Dictionary;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.felix.cm.impl.TestCaseInsensitiveDictionary;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * {@link ConfigurationMap} unit tests.
 *
 * @since 1.4
 */
public class ConfigurationMapTest
{

  @Test
  public void test() {
    Dictionary<String, Object> dict = new TestCaseInsensitiveDictionary();
    ConfigurationMap underTest = new ConfigurationMap(dict);

    dict.put("foo", "bar");
    assertThat(underTest.size(), is(1));
    assertThat(underTest.containsKey("FOO"), is(true));
    assertThat(underTest.get("Foo"), CoreMatchers.<Object>is("bar"));
    underTest.put("foo", "car");
    assertThat(dict.get("foo"), CoreMatchers.<Object>is("car"));
    dict.put("foo", "xar");
    assertThat(underTest.get("Foo"), CoreMatchers.<Object>is("xar"));
    dict.remove("foo");
    assertThat(underTest.size(), is(0));
    underTest.put("foo", "bar");
    assertThat(dict.size(), is(1));
    underTest.remove("foo");
    assertThat(dict.size(), is(0));
    underTest.put("foo", "bar");
    assertThat(dict.size(), is(1));
    underTest.clear();
    assertThat(dict.size(), is(0));
    dict.put("foo", "bar");
    Iterator<Entry<String, Object>> it = underTest.entrySet().iterator();
    assertThat(it.hasNext(), is(true));
    Entry<String, Object> entry = it.next();
    assertThat(entry, is(notNullValue()));
    assertThat(entry.getKey(), is("foo"));
    assertThat(entry.getValue(), CoreMatchers.<Object>is("bar"));
    entry.setValue("car");
    assertThat(entry.getValue(), CoreMatchers.<Object>is("car"));
    assertThat(dict.get("foo"), CoreMatchers.<Object>is("car"));
    assertThat(it.hasNext(), is(false));
    it.remove();
    assertThat(dict.size(), is(0));
    assertThat(underTest.size(), is(0));
  }

}
