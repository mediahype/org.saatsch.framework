package org.saatsch.framework.base.jfxbase.control;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class Label extends javafx.scene.control.Label implements ExtendedNode<Label> {
  
  public Label() {
    super();

  }

  public Label(String text, Node graphic) {
    super(text, graphic);
  }

  public Label(String text) {
    super(text);
  }

  public Label withTextProperty(StringProperty property){
    textProperty().bind(property);
    return this;
  }

  
}
