package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.base.jfxbase.BeanTable;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.EditPropertyDialog;

public class RightPaneTable extends AbstractRightPane {

  private final BeanTable table;

  public RightPaneTable() {
    super();

    table = new BeanTable(false).displayType(false);
    setContent(table);


    table.addEventHandler(MouseEvent.MOUSE_CLICKED, click -> {
      if (click.getClickCount() == 2) {
        TreeItem<Object> selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null){
          return;
        }

        if (!(selectedItem.getValue() instanceof Property)){
          return;
        }

        EditPropertyDialog diag = new EditPropertyDialog((Property<Object>) selectedItem.getValue(), table.getBean());
        diag.showAndWait();
        table.refresh();

      }
    });


  }



  @Override
  public void selectionChanged(Bean newSelection) {
    super.selectionChanged(newSelection);
    clear();
    fill(newSelection);
  }

  private void fill(Bean newSelection) {
    table.setBean(newSelection);
  }

  private void clear() {
  }
}
