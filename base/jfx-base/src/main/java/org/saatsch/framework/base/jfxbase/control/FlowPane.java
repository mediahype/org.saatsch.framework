package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;

public class FlowPane extends javafx.scene.layout.FlowPane implements ExtendedNode<FlowPane> {

  public FlowPane withChildren(Node... elements) {
    getChildren().addAll(elements);
    return this;
  }

}
