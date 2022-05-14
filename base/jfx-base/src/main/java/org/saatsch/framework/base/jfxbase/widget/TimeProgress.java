package org.saatsch.framework.base.jfxbase.widget;

import javafx.beans.property.StringProperty;
import javafx.scene.control.ProgressBar;

import java.time.ZonedDateTime;

/**
 * progress bar with start and finish time. To update, call {@link #tick()}.
 */
public class TimeProgress extends ProgressBar {

  private final TimeProgressModel model;

  private boolean inverted = false;

  /**
   * create a new {@link TimeProgress}
   *
   * @param start the start time
   * @param finish the finish time
   */
  public TimeProgress(ZonedDateTime start, ZonedDateTime finish) {
    super(0);
    this.model = new TimeProgressModel(start, finish);
  }

  public TimeProgress withInverted(){
    inverted = true;
    return this;
  }

  /**
   * updates the progress value.
   */
  public void tick(){
    if (inverted){
      setProgress(model.percentLeft());
    }else{
      setProgress(model.percentDone());
    }
    model.timeLeftDisplay();
  }

  /**
   * @return a {@link StringProperty} of the time left. The property is updated along with calls to {@link #tick()}.
   */
  public StringProperty timeLeftDisplay(){
    return model.timeLeftDisplay();
  }



}
