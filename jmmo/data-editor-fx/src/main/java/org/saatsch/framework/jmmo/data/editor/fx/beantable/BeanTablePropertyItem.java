package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.EditPropertyDialog;

public class BeanTablePropertyItem extends TreeItem<Object> {

  public BeanTablePropertyItem(Property property) {

    setValue(property);

    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent click) {
        if (click.getClickCount() == 2) {
          EditPropertyDialog diag = new EditPropertyDialog(property, getParent().getValue());
          diag.showAndWait();

          // treeViewer.refresh();
        }
      }
    });

  }
}
