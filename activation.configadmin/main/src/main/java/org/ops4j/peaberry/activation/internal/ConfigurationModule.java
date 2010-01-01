package org.ops4j.peaberry.activation.internal;

import java.util.Dictionary;
import java.util.Enumeration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Named;

import static com.google.inject.name.Names.*;

@SuppressWarnings("unchecked")
public class ConfigurationModule extends AbstractModule {
  private final String pid;
  private final Dictionary props;
  
  public ConfigurationModule(String pid, Dictionary props) {
    this.pid = pid;
    this.props = props;
  }
  
  @Override
  protected void configure() {
    for (Enumeration e = props.keys(); e.hasMoreElements();) {
      final String key = (String) e.nextElement();
      final Object val = props.get(key);
      final Named tag = named(pid + "." + key);
      
      if (val instanceof Long) {
        bindConstant().annotatedWith(tag).to((Long) val);
      }
      else if (val instanceof Integer) {
        bindConstant().annotatedWith(tag).to((Integer) val);
      }
      else if (val instanceof Short) {
        bindConstant().annotatedWith(tag).to((Short) val);
      }
      else if (val instanceof Byte) {
        bindConstant().annotatedWith(tag).to((Byte) val);
      }
      else if (val instanceof Character) {
        bindConstant().annotatedWith(tag).to((Character) val);
      }
      else if (val instanceof Double) {
        bindConstant().annotatedWith(tag).to((Double) val);
      }
      else if (val instanceof Float) {
        bindConstant().annotatedWith(tag).to((Float) val);
      }
    }
  }
}
