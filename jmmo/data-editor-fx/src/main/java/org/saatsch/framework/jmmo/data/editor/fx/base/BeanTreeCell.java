package org.saatsch.framework.jmmo.data.editor.fx.base;

import javafx.scene.control.TreeCell;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class BeanTreeCell extends TreeCell<Bean> {


  @Override
  protected void updateItem(Bean item, boolean empty) {
    super.updateItem(item, empty);

    if (item != null) {
      setText(PropertyUtil.getFullName(item));      
    }
    

  }
}
