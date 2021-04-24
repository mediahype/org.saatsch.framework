package org.saatsch.framework.base.jfxbase.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * extension of JFX Button. Provides a fluent API.
 * 
 * @author saatsch
 *
 */
public class Button extends javafx.scene.control.Button implements ExtendedControl<Button> {

  public Button() {
    super();
  }

  public Button(String text, Node graphic) {
    super(text, graphic);
  }

  public Button(String text) {
    super(text);
  }

  public Button withAction(EventHandler<ActionEvent> value) {
    super.setOnAction(value);
    return this;
  }

   
}
