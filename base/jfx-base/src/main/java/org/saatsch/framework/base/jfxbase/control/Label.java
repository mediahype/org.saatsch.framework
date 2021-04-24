package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;

public class Label extends javafx.scene.control.Label implements ExtendedControl<Label> {
  
  public Label() {
    super();
  }

  public Label(String text, Node graphic) {
    super(text, graphic);
  }

  public Label(String text) {
    super(text);
  }


  
}
