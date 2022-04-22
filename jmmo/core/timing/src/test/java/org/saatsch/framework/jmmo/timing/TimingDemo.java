package org.saatsch.framework.jmmo.timing;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates a ticker that writes a log output every second. It uses {@link NonStopLoop}. When observing the output of this demo it is visible that ticks occur
 * almost exactly at the correct time. This is achieved by using a high framerate (120 per second).
 */
public class TimingDemo extends NonStopLoop {

  private static final Logger LOG = LoggerFactory.getLogger(TimingDemo.class);

  /**
   * second at which the last update call occurred.
   */
  private int lastSecond;

  @Test
  public void test() throws InterruptedException {

    TimingDemo loop = new TimingDemo();
    loop.start(true, "loop-thread", 120, true);

    TimeUnit.SECONDS.sleep(10);

  }

  @Override
  public void update() {

    int second = LocalTime.now().getSecond();

    if (second != lastSecond) {
      lastSecond = second;
      LOG.info("new second");
    }


  }
}
