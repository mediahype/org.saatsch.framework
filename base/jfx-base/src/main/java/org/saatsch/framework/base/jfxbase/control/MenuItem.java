package org.saatsch.framework.base.jfxbase.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;

public class MenuItem extends javafx.scene.control.MenuItem  {

  public MenuItem(String text) {
    super(text);
  }

  public MenuItem withParent(Menu menuMain) {
    menuMain.getItems().add(this);
    return this;
  }

  public MenuItem withAction(EventHandler<ActionEvent> value) {
    super.setOnAction(value);
    return this;
  }

  public MenuItem withHotkey(KeyCodeCombination hotkey){
    setAccelerator(hotkey);
    return this;
  }
  
}
