package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;

public class VBox extends javafx.scene.layout.VBox implements ExtendedNode<VBox> {
  
  public VBox withChildren(Node... elements) {
    getChildren().addAll(elements);
    return this;
  }

}
