package org.saatsch.framework.base.jfxbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.joda.beans.Bean;

public class BeanTableBeanItem extends TreeItem<Object> {


  private final Bean bean;

  public BeanTableBeanItem(Bean bean) {
    this.bean = bean;
  }

  @Override
  public boolean isLeaf() {
    return false;
  }

  @Override
  public ObservableList<TreeItem<Object>> getChildren() {

    ObservableList<TreeItem<Object>> ret = FXCollections.observableArrayList();

    bean.propertyNames().forEach(name -> {
      ret.add(new BeanTablePropertyItem(bean.property(name)));
    });

    return ret;
  }
}
