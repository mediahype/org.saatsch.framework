package org.saatsch.framework.base.jfxbase.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;

public class ToggleButton extends javafx.scene.control.ToggleButton implements ExtendedNode<ToggleButton> {

  public ToggleButton() {
    super();
  }

  public ToggleButton(String text, Node graphic) {
    super(text, graphic);
  }

  public ToggleButton(String text) {
    super(text);
  }
  
  public ToggleButton withAction(EventHandler<ActionEvent> value) {
    super.setOnAction(value);
    return this;
  }
  
  public ToggleButton withGroup(ToggleGroup group) {
    super.setToggleGroup(group);
    return this;
  }
  
}
