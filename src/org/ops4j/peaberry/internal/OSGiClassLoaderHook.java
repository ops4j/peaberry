package com.google.inject.osgi.internal;

import com.google.inject.ClassLoaderHook;

public final class OSGiClassLoaderHook
  implements ClassLoaderHook {

  static final class BridgeClassLoader extends ClassLoader {

    static final ClassLoader PEABERRY_LOADER =
      BridgeClassLoader.class.getClassLoader();

    public BridgeClassLoader(final Class<?> type) {
      super(type.getClassLoader());
    }

    @Override
    protected Class<?> loadClass(final String name, final boolean resolve)
      throws ClassNotFoundException {

      if (PEABERRY_LOADER != null && name.startsWith("com.google.inject")) {
        final Class<?> clazz = PEABERRY_LOADER.loadClass(name);
        if (resolve) {
          super.resolveClass(clazz);
        }
        return clazz;
      }

      return super.loadClass(name, resolve);
    }
  }

  public ClassLoader get(Class<?> type) {
    return new BridgeClassLoader(type);
  }
}
