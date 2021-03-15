package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.ScrollPane;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;

public abstract class AbstractRightPane extends ScrollPane implements SelectionChanged<Bean> {

  public AbstractRightPane() {
    setFitToWidth(true);
    setFitToHeight(true);
  }

  private Bean currentSelection;


  @Override
  public void selectionChanged(Bean newSelection) {
    currentSelection = newSelection;
  }

  public Bean getCurrentSelection() {
    return currentSelection;
  }

}
