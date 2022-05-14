package org.saatsch.framework.base.jfxbase.widget;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Duration;
import java.time.ZonedDateTime;

public class TimeProgressModel {

  public final Duration duration;
  public ZonedDateTime start;
  public ZonedDateTime finish;

  private StringProperty timeLeftDisplay = new SimpleStringProperty();

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

  /**
   * @return the percentage of time that is passed at the moment of the invocation.
   */
  public double percentDone(){
    return (double) timePassed().toMillis() / (double) duration.toMillis();
  }

  public double percentLeft(){
    double v = (double) timeLeft().toMillis() / (double) duration.toMillis();
    // do not return values less than zero.
    return v < 0 ? 0 : v;
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

  /**
   * recalculates and returns the time left.
   *
   * @return the time left as StringProperty
   */
  public StringProperty timeLeftDisplay(){
    timeLeftDisplay.setValue(format(timeLeft()).toString());
    return timeLeftDisplay;
  }

  private StringBuilder format(Duration dur){
    long hours = dur.toHours();
    int minutes = dur.toMinutesPart();
    long seconds = dur.toSecondsPart();

    StringBuilder sb = new StringBuilder();

    if (hours > 0){
      sb.append(String.format("%d:%02d:%02d", hours, minutes, seconds));
      return sb;
    }

    if (minutes > 0){
      sb.append(String.format("%02d:%02d", minutes, seconds));
      return sb;
    }

    if (seconds > 0){
      sb.append(seconds);
      return sb;
    }

    return sb;

  }


}
