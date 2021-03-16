package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import org.joda.beans.Bean;
import org.joda.beans.Property;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class BeanTablePropertyItem extends TreeItem<Object> {


  private Property<Object> property;
  private ObservableList<TreeItem<Object>> children;
  
  

  public BeanTablePropertyItem(Property<Object> property) {
    setValue(property);
    this.property = property;
  }
  
  @Override
  public boolean isLeaf() {
    if ( property.get() instanceof Bean ) {
      return false;
    }
    return true;
  }
  
  @Override
  public ObservableList<TreeItem<Object>> getChildren() {


    if ( property.get() instanceof Bean && children == null ) {
      children = Util.getChildren((Bean) property.get());
      
    }

    return children;
    
  }
  
}
