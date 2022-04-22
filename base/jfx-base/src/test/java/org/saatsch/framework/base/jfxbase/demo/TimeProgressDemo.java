package org.saatsch.framework.base.jfxbase.demo;


import javafx.application.Application;
import org.junit.Test;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.base.jfxbase.widget.TimeProgressModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class TimeProgressDemo extends AbstractDemo {
  @Override
  protected void fill(VBox root) {





  }

  @Test
  public void test() {
    Application.launch(this.getClass());
  }

  @Test
  public void test2() throws InterruptedException {
    System.out.println("Now: " +LocalDateTime.now());
    System.out.println("1000 hours: " + Duration.ofHours(1000));

    TimeProgressModel tp = new TimeProgressModel(ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(10));

    System.out.println("tp: " + tp);
    for (int i= 0; i<10; i++){
      TimeUnit.SECONDS.sleep(1);
      System.out.println("percent done: " + tp.timePassed().toMillis() +" --- " + tp. duration.toMillis() + " --- " + tp.percentDone());
    }




  }

}
