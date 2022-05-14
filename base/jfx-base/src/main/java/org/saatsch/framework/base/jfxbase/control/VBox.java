package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;
import javafx.scene.layout.Priority;

public class VBox extends javafx.scene.layout.VBox implements ExtendedNode<VBox> {
  
  public VBox withChildren(Node... elements) {
    getChildren().addAll(elements);
    return this;
  }

  /**
   * adds the given element to the list of children and sets it to always vertically grow.
   * @param element the element to add.
   * @return this.
   */
  public VBox withStretchedChild(Node element) {
    getChildren().add(element);
    VBox.setVgrow(element, Priority.ALWAYS);
    return this;
  }

}
