package org.saatsch.framework.base.jfxbase.widget;

import java.time.Duration;
import java.time.ZonedDateTime;

public class TimeProgressModel {

  public final Duration duration;
  public ZonedDateTime start;
  public ZonedDateTime finish;

  private Duration bonus = Duration.ZERO;

  public TimeProgressModel(ZonedDateTime start, ZonedDateTime finish) {
    this.start = start;
    this.finish = finish;
    duration = Duration.between(start, finish);

  }

  public void withBonus(Duration toAdd){
    bonus = bonus.plus(toAdd);
  }

  public Duration timeLeft(){
    return Duration.between(ZonedDateTime.now().plus(bonus), finish);
  }

  public Duration timePassed(){
    return duration.minus(timeLeft());
  }

  public double percentDone(){
    return (double) timePassed().toMillis() / (double) duration.toMillis();
  }

  @Override
  public String toString() {
    return "TimeProgressModel{" +
        "duration=" + duration +
        ", start=" + start +
        ", finish=" + finish +
        ", bonus=" + bonus +
        '}';
  }
}
