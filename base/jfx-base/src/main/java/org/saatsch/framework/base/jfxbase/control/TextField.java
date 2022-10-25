package org.saatsch.framework.base.jfxbase.control;

import javafx.beans.value.ChangeListener;
import javafx.scene.text.Font;

public class TextField extends javafx.scene.control.TextField implements ExtendedNode<TextField> {

  public TextField() {
    super();
  }

  public TextField(String text) {
    super(text);
  }

  public TextField withChangeListener(ChangeListener<String> listener){
    textProperty().addListener(listener);
    return this;
  }

  public TextField withMonospace(){
    setFont(Font.font("monospace"));
    return this;
  }

}
