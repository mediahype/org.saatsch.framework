package org.saatsch.framework.base.jfxbase.control;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public interface ExtendedControl<T> {

  /**
   * adds this to the children of the given parent Pane. Only works if this is a {@link Node}.
   * 
   * @param parent the parent Pane
   * @return this.
   */
  default T withParent(Pane parent) {
    parent.getChildren().add((Node) this);
    return (T) this;
  }

  /**
   * adds this to the tabs of the given {@link TabPane}. Only works if this is a {@link Tab}
   * 
   * @param parent the parent {@link TabPane}.
   * @return this.
   */
  default T withParent(TabPane parent) {
    parent.getTabs().add((Tab) this);
    return (T) this;
  }



  /**
   * Sets the column,row indeces for the child when contained in a gridpane. Only works if this is a
   * {@link Node}.
   * 
   * @param columnIndex the column index position for the child
   * @param rowIndex the row index position for the child
   * @return this
   */
  default T withLayoutColRow(int columnIndex, int rowIndex) {
    GridPane.setConstraints((Node) this, columnIndex, rowIndex);
    return (T) this;
  }

  /**
   * Only works if this is a {@link Node}.
   * 
   * @param value
   * @return
   */
  default T withLayoutHalign(HPos value) {
    GridPane.setHalignment((Node) this, value);
    return (T) this;
  }

}
