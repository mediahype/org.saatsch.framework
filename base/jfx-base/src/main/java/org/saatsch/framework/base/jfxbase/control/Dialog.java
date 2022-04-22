package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;

import java.util.Arrays;

public class Dialog<T> extends javafx.scene.control.Dialog<T> {

  public Dialog() {
    super();
  }

  public Dialog<T> withButton(ButtonType button) {
    getDialogPane().getButtonTypes().add(button);
    return this;
  }

  public Dialog<T> withButtons(ButtonType... buttons) {
    Arrays.stream(buttons).iterator().forEachRemaining(button -> getDialogPane().getButtonTypes().add(button));
    return this;
  }

  public Dialog<T> withContent(Node content){
    getDialogPane().setContent(content);
    return this;
  }

}
