package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

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

      Property<Object> property = bean.property(name);

      if (!PropertyUtil.isPropertyAnnotatedWith(property, JmmoEditorHidden.class)){
        ret.add(new BeanTablePropertyItem(bean.property(name)));
      }



    });

    return ret;
  }
}
