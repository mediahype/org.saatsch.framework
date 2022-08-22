package org.saatsch.framework.jmmo.data.editor.fx.beantree;

import javafx.scene.control.TreeCell;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeansTreeCell extends TreeCell<Bean> {

  private static final Logger LOG = LoggerFactory.getLogger(BeansTreeCell.class);

  @Override
  protected void updateItem(Bean item, boolean empty) {
    super.updateItem(item, empty);

    if (item != null) {
      setText(PropertyUtil.getFullName(item));      
    } else {
      setText(null);
    }

  }

}
