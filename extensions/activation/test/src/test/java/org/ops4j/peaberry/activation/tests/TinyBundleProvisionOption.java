/**
 * Copyright (C) 2009 Todor Boev
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
package org.ops4j.peaberry.activation.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.ops4j.pax.exam.options.AbstractProvisionOption;
import org.ops4j.pax.exam.options.ProvisionOption;
import org.ops4j.pax.swissbox.tinybundles.core.BuildableBundle;
import org.ops4j.pax.swissbox.tinybundles.core.TinyBundle;
import org.ops4j.pax.swissbox.tinybundles.core.TinyBundles;

/**
 * Fuses the TinyBundle and ProvisionOption fluent APIs. The goals is to allow
 * the user to specify the noStart() and update() options. Also in this way we
 * avoid the need to use a wrapper "provision()" statement for the tiny bundles.
 * Currently it is required to store the tiny bundle on the disk so a "file:"
 * URL can be returned to PaxExam. The bundles are stored in
 * "${basedir}/target/test-bundles". I.e. this class must be used under Maven
 * only.
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 */
public class TinyBundleProvisionOption extends AbstractProvisionOption<TinyBundleProvisionOption> {
  private static final String WORKDIR = System.getProperty("basedir") + "/target/test-bundles";
  
  private static int id;

  private synchronized int nextId() {
    return id++;
  }
  
  public static TinyBundleProvisionOption tinyBundle() {
    return new TinyBundleProvisionOption();
  }

  private final String path;
  private final String url;
  private final TinyBundle bundle;

  private TinyBundleProvisionOption() {
    this.bundle = TinyBundles.newBundle();
    this.path = WORKDIR + "/bundle-" + nextId() + ".jar";
    this.url = "file:" + path;
  }

  @Override
  protected TinyBundleProvisionOption itself() {
    return this;
  }

  public String getURL() {
    return url;
  }

  public TinyBundleProvisionOption add(Class<?> content) {
    bundle.add(content);
    return this;
  }

  public TinyBundleProvisionOption add(String name, URL content) {
    bundle.add(name, content);
    return this;
  }

  public TinyBundleProvisionOption add(String name, InputStream content) {
    bundle.add(name, content);
    return this;
  }

  public TinyBundleProvisionOption removeHeader(String key) {
    bundle.removeHeader(key);
    return this;
  }

  public TinyBundleProvisionOption removeResource(String key) {
    bundle.removeResource(key);
    return this;
  }

  public TinyBundleProvisionOption set(String key, String value) {
    bundle.set(key, value);
    return this;
  }

  public ProvisionOption<TinyBundleProvisionOption> build(BuildableBundle builder) {
    packageToFile(builder);
    return this;
  }

  public ProvisionOption<TinyBundleProvisionOption> build() {
    packageToFile(null);
    return this;
  }

  private void packageToFile(BuildableBundle builder) {
    try {
      /* Lazy-create the temporary storage directory */
      new File(WORKDIR).mkdir();
      
      final InputStream in = (builder != null) ? bundle.build(builder) : bundle.build();
      final FileOutputStream out = new FileOutputStream(path);

      byte[] buff = new byte[1024];
      for (int read = in.read(buff); read > 0; read = in.read(buff)) {
        out.write(buff, 0, read);
      }
      out.close();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
