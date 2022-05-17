package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;
import javafx.scene.layout.Priority;

public class HBox extends javafx.scene.layout.HBox implements ExtendedNode<HBox>{

  public HBox withChildren(Node... elements) {
    getChildren().addAll(elements);
    return this;
  }

  /**
   * adds the given element to the list of children and sets it to always horizontally grow.
   * @param element the element to add.
   * @return this.
   */
  public HBox withStretchedChild(Node element) {
    getChildren().add(element);
    HBox.setHgrow(element, Priority.ALWAYS);
    return this;
  }
}
