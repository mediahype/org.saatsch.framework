package org.saatsch.framework.jmmo.data.editor.fx.composite;

import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.beantree.BeanTree;
import org.saatsch.framework.jmmo.data.editor.fx.beantree.BeanTreeFactory;

public class FilterableBeanTree extends VBox {

  private final BeanTree beanTree;
  private final TextField txtFilter;

  public FilterableBeanTree(Class<? extends Bean> clazz) {
    beanTree = BeanTreeFactory.create(clazz);
    VBox.setVgrow(beanTree, Priority.ALWAYS);

    txtFilter = new TextField();
    getChildren().add(txtFilter);
    getChildren().add(beanTree);

  }

  public void setSelectionChangedListener(SelectionChanged<Bean> listener) {
    beanTree.setSelectionChangedListener(listener);
  }

  public Bean getSelection(){
    return beanTree.getSelection();
  }

}
