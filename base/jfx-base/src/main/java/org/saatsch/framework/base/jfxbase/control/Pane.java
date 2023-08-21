package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;

public class Pane extends javafx.scene.layout.Pane implements ExtendedNode<Pane> {

  public Pane withChildren(Node... elements) {
    getChildren().addAll(elements);
    return this;
  }

}
