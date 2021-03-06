package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import org.joda.beans.Bean;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class BeanTableBeanItem extends TreeItem<Object> {


  private final Bean bean;
  private ObservableList<TreeItem<Object>> children;

  public BeanTableBeanItem(Bean bean) {
    this.bean = bean;
  }

  @Override
  public boolean isLeaf() {
    return false;
  }

  @Override
  public ObservableList<TreeItem<Object>> getChildren() {

    if (children == null) {
      children = Util.getChildren(bean);
    }

    return children;
  }
}
