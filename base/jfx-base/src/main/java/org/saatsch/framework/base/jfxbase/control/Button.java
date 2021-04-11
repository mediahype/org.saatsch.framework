package org.saatsch.framework.base.jfxbase.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * extension of JFX Button. Provides a fluent API.
 * 
 * @author saatsch
 *
 */
public class Button extends javafx.scene.control.Button {

  public Button() {
    super();
  }

  public Button(String text, Node graphic) {
    super(text, graphic);
  }

  public Button(String text) {
    super(text);
  }

  public Button onAction(EventHandler<ActionEvent> value) {
    super.setOnAction(value);
    return this;
  }

  /**
   * adds this Button to a pane.
   *
   * @param parent the pane to add this Button to.
   * @return this.
   */
  public Button addTo(Pane parent) {
    parent.getChildren().add(this);
    return this;
  }
  
  /**
   * calls {@link #addTo(Pane)}.
   * 
   * @param parent
   * @return
   */
  public Button withParent(Pane parent) {
    return addTo(parent);
  }
  
}
