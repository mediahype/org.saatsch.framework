package org.saatsch.framework.jmmo.clustering;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.networking.api.vo.ServerError;

/**
 * a {@link Runnable} that runs inside a try catch block and logs an error on any exception. This is
 * extremely useful, because normally exceptions in {@link Runnable}s are swallowed if not caught.
 *
 * @author saatsch
 */
public abstract class TryCatchTask implements Runnable {

  private static final Logger LOG = LoggerFactory.getLogger(TryCatchTask.class);

  @Override
  public void run() {
    try {
      long start = System.currentTimeMillis();
      runTask();
      LOG.debug("Task:{} --- Duration:{} ms", this.getClass().getSimpleName(), System.currentTimeMillis() - start);
    } catch (Exception e) {
      handle(e);
      LOG.error("Error while running Task of type:{} ", this.getClass());
      LOG.error("", e);
    }

  }

  private void handle(Exception e) {
    if (SessionLocalTask.class.isAssignableFrom(getClass())) {
      SessionLocalTask me = (SessionLocalTask) this;

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      
      e.printStackTrace(pw);

      // TODO: at this early point in development, we respond with the server error.
      //  This is not desireable and must be made configurable with the default of not to give away
      //  the raw error to the client.
      me.respond(new ServerError(sw.toString()));

      try {
        sw.close();
      } catch (IOException e1) {
      }
      pw.close();
    }

  }

  /**
   * this method is called by {@link Runnable#run()} and wrapped in a try catch block for your
   * convenience. It is the entry point to running the task at hand.
   */
  public abstract void runTask();

}
