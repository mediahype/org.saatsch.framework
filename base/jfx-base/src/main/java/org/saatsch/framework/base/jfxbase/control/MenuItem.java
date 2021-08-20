package org.saatsch.framework.base.jfxbase.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuItem extends javafx.scene.control.MenuItem  {

  public MenuItem(String text) {
    super(text);
  }

  public MenuItem withParent(Menu menuMain) {
    menuMain.getItems().add(this);
    return this;
  }

  public MenuItem withAction(@SuppressWarnings("exports") EventHandler<ActionEvent> value) {
    super.setOnAction(value);
    return this;
  }
  
}
