package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public interface ExtendedControl<T> {

  default T withParent(Pane parent) {
    parent.getChildren().add((Node) this);
    return (T) this;
  }
  
  /**
   * Sets the column,row indeces for the child when contained in a gridpane.
     * @param columnIndex the column index position for the child
     * @param rowIndex the row index position for the child
   * @return this
   */
  default T withColRow(int columnIndex, int rowIndex) {
    GridPane.setConstraints((Node) this, columnIndex, rowIndex);
    return (T) this;
  }
  
}
