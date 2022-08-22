package org.saatsch.framework.base.jfxbase.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;

public class RadioButton extends javafx.scene.control.RadioButton implements ExtendedNode<RadioButton> {

  public RadioButton() {
  }

  public RadioButton(String text) {
    super(text);
  }

  public RadioButton withToggleGroup(ToggleGroup toggleGroup){
    setToggleGroup(toggleGroup);
    return this;
  }

  public RadioButton withAction(EventHandler<ActionEvent> value) {
    super.setOnAction(value);
    return this;
  }

}
