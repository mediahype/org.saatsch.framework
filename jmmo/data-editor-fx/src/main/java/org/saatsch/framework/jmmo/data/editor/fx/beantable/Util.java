package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class Util {


  public static ObservableList<TreeItem<Object>> getChildren(Bean bean) {

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
