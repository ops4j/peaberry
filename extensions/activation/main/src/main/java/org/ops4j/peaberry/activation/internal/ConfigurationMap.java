package org.ops4j.peaberry.activation.internal;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A map implementation backed by a (case insensitive) dictionary as specified by Configuration Admin OSGi compendium.
 *
 * @since 1.4
 */
class ConfigurationMap
    extends AbstractMap<String, Object>
{

  private final Dictionary<String, Object> dict;

  public ConfigurationMap(final Dictionary<String, Object> dict) {
    this.dict = dict;
  }

  @Override
  public boolean containsKey(final Object key) {
    if (key != null && !(key instanceof String)) {
      return false;
    }
    for (Enumeration e = dict.keys(); e.hasMoreElements(); ) {
      Object element = e.nextElement();
      if (element == null) {
        return key == null;
      }
      if (((String) element).equalsIgnoreCase((String) key)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object get(final Object key) {
    return dict.get(key);
  }

  @Override
  public Object put(final String key, final Object value) {
    return dict.put(key, value);
  }

  @Override
  public Object remove(final Object key) {
    return dict.remove(key);
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return new AbstractSet<Entry<String, Object>>()
    {
      @Override
      public Iterator<Entry<String, Object>> iterator() {
        return new Iterator<Entry<String, Object>>()
        {
          final Enumeration<?> elements = dict.keys();

          String currentKey;

          public boolean hasNext() {
            return elements.hasMoreElements();
          }

          public Entry<String, Object> next() {
            try {
              currentKey = elements.nextElement().toString();
            }
            catch (NoSuchElementException e) {
              currentKey = null;
            }
            return new Entry<String, Object>()
            {
              private final String key = currentKey;

              public String getKey() {
                return key;
              }

              public Object getValue() {
                return dict.get(key);
              }

              public Object setValue(final Object value) {
                Object currentVal = getValue();
                dict.put(key, value);
                return currentVal;
              }

              @Override
              public boolean equals(final Object o) {
                if (!(o instanceof Entry)) {
                  return false;
                }
                Entry e = (Entry) o;
                Object value = dict.get(e.getKey());
                return value == null ? e.getValue() == null : value.equals(e.getValue());
              }
            };
          }

          public void remove() {
            if (currentKey == null) {
              throw new IllegalStateException();
            }
            dict.remove(currentKey);
          }
        };
      }

      @Override
      public int size() {
        return dict.size();
      }
    };
  }

}
