package org.saatsch.framework.base.jfxbase.control;

import javafx.geometry.Orientation;
import javafx.scene.Node;

public class SplitPane extends javafx.scene.control.SplitPane implements ExtendedNode<SplitPane> {

  /**
   * sets the orientation to vertical (horizontal is the default, thus there is no method for setting it to horizontal).
   * Note that "vertical" means that the movement of the splitter will be vertical.
   * 
   * @return this
   */
  public SplitPane vertical() {
    setOrientation(Orientation.VERTICAL);
    return this;
  }

  public SplitPane withChildren(Node... elements) {
    getItems().addAll(elements);
    return this;
  }

}
