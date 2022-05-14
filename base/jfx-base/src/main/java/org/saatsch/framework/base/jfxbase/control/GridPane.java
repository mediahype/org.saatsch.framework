package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

public class GridPane extends javafx.scene.layout.GridPane implements ExtendedNode<GridPane> {

  /**
   * prepares a number of columns to be used in this GridPane by simply adding {@link ColumnConstraints}.
   * This is the prerequisite for adding per-column layout information.
   * You only need this, if you want to add layout information prior to adding content.
   *
   * @param numColumns the number of columns
   * @return this
   */
  public GridPane withColumns(int numColumns) {

    for (int i = 0; i < numColumns; i++) {
      getColumnConstraints().add(new ColumnConstraints());
    }

    return this;
  }

  public GridPane withStretchedColumn(int columnIndex) {
    getColumnConstraints().get(columnIndex).setHgrow(Priority.ALWAYS);
    return this;
  }

}
