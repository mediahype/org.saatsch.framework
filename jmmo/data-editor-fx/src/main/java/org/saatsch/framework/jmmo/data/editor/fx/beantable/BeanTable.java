package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.EditPropertyDialog;

public class BeanTable extends TreeTableView<Object> {

  private TreeItem<Object> root;
  private Bean bean;

  public BeanTable() {

    TreeTableColumn<Object, String> nameColumn = new TreeTableColumn<>("Property Name");
    TreeTableColumn<Object, String> valueColumn = new TreeTableColumn<>("Property Value");

    valueColumn.setCellValueFactory(new ValueLabelProvider());
    nameColumn.setCellValueFactory(new NameLabelProvider());

    valueColumn.setMaxWidth(500);

    getColumns().add(nameColumn);
    getColumns().add(valueColumn);

    // setShowRoot(false);


    addEventHandler(MouseEvent.MOUSE_CLICKED, click -> {
      if (click.getClickCount() == 2) {
        EditPropertyDialog diag = new EditPropertyDialog((Property<Object>) getSelectionModel().getSelectedItem().getValue(), bean);
        diag.showAndWait();
        refresh();

      }
    });

  }

  public void setBean(Bean bean){
    this.bean = bean;

    root = new BeanTableBeanItem(bean);
    root.setExpanded(true);
    setRoot(root);
    

  }

}
