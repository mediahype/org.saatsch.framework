package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import javafx.scene.control.TreeItem;
import org.joda.beans.Property;

public class BeanTablePropertyItem extends TreeItem<Object> {

  public BeanTablePropertyItem(Property property) {

    setValue(property);

  }
}
