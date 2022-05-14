package org.saatsch.framework.base.jfxbase.demo;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import org.junit.Test;
import org.saatsch.framework.base.jfxbase.control.HBox;
import org.saatsch.framework.base.jfxbase.control.Label;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.base.jfxbase.widget.TimeProgress;

import java.time.ZonedDateTime;

public class TimeProgressDemo extends AbstractDemo {

  private TimeProgress timeProgress;
  private TimeProgress timeProgress2;

  @Override
  protected void fill(VBox root) {
    root.setPrefWidth(200);

    timeProgress = new TimeProgress(ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(10));
    timeProgress2 = new TimeProgress(ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(10)).withInverted();

    HBox box = new HBox().withChildren(timeProgress, new Label().withTextProperty(timeProgress.timeLeftDisplay()));
    root.withChildren(box, timeProgress2);

    Timeline everySecond = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
      timeProgress.tick();
      timeProgress2.tick();
    }));
    everySecond.setCycleCount(Timeline.INDEFINITE);
    everySecond.play();
  }

  @Test
  public void test() throws InterruptedException {
    Application.launch(this.getClass());
  }


}
