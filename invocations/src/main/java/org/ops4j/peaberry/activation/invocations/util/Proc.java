package org.ops4j.peaberry.activation.invocations.util;

import java.util.concurrent.Callable;

public abstract class Proc implements Callable<Void> {
  public final Void call() throws Exception {
    run();
    return null;
  }

  protected abstract void run() throws Exception;
}
