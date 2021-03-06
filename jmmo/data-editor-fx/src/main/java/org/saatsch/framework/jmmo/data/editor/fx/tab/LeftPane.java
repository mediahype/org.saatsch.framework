package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.composite.FilterableBeanTree;

/**
 * the left pane on an editor tab.
 */
public class LeftPane extends VBox {

  private final EditorTab parent;
  private final FilterableBeanTree beanTree;

  public LeftPane(EditorTab parent){

    this.parent = parent;
    beanTree = new FilterableBeanTree(parent.getObjectClass());
    getChildren().add(beanTree);
    setId(parent.getObjectClass() + "LeftPane");

  }


  public void setSelectionChangedListener(SelectionChanged<Bean> listener) {
    beanTree.setSelectionChangedListener(listener);
  }

  /**
   * selects an object in the bean tree
   *
   * @param pointer the pointer to the object
   * @return true if the object was selected.
   */
  public boolean selectObject(Pointer pointer) {

    return beanTree.selectObject(pointer);

  }
}
