package org.saatsch.framework.jmmo.clustering;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * cluster unaware wrapper around an {@link ExecutorService}.
 *
 */
public class Exec {

  private ExecutorService pool;

  public Exec() {
    init();
  }
  
  private void init() {
    pool = Executors.newFixedThreadPool(10);
  }

  public void exec(Runnable command) {

    pool.execute(command);

  }

}
