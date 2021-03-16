package org.saatsch.framework.base.jfxbase;

import javafx.scene.control.TreeItem;
import org.joda.beans.Property;

public class BeanTablePropertyItem extends TreeItem<Object> {

  public BeanTablePropertyItem(Property<Object> property) {
    setValue(property);
  }
  
  
  
}
