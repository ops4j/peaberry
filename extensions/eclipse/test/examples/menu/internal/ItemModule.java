/**
 * Copyright (C) 2009 Stuart McCulloch
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

package examples.menu.internal;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import examples.menu.Item;

/**
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public class ItemModule
    extends AbstractModule {

  @Override
  protected void configure() {
    bindConstant().annotatedWith(Names.named("label")).to("Injected menu item");
    bind(Item.class).to(ItemImpl.class);
  }
}
