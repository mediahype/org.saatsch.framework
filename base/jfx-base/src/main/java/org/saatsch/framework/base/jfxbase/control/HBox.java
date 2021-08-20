package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;

public class HBox extends javafx.scene.layout.HBox implements ExtendedNode<HBox>{

  public HBox withChildren(Node... elements) {
    getChildren().addAll(elements);
    return this;
  }

}
