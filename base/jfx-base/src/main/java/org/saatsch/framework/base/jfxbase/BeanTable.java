package org.saatsch.framework.base.jfxbase;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.joda.beans.Bean;

public class BeanTable extends TreeTableView<Object> {

  private TreeItem<Object> root;
  private Bean bean;

  public BeanTable() {

    TreeTableColumn<Object, String> nameColumn = new TreeTableColumn<>("Property Name");
    TreeTableColumn<Object, String> typeColumn = new TreeTableColumn<>("Type");
    TreeTableColumn<Object, String> valueColumn = new TreeTableColumn<>("Property Value");
    
    valueColumn.setCellValueFactory(new ValueLabelProvider());
    typeColumn.setCellValueFactory(new TypeLabelProvider());
    nameColumn.setCellValueFactory(new NameLabelProvider());


    getColumns().clear();
    
    getColumns().add(nameColumn);
    getColumns().add(typeColumn);
    getColumns().add(valueColumn);

    setShowRoot(false);

//    addEventHandler(MouseEvent.MOUSE_CLICKED, click -> {
//      if (click.getClickCount() == 2) {
//        EditPropertyDialog diag = new EditPropertyDialog((Property<Object>) getSelectionModel().getSelectedItem().getValue(), bean);
//        diag.showAndWait();
//        refresh();
//
//      }
//    });

  }

  public void setBean(Bean bean){
    this.bean = bean;

    root = new BeanTableItem(bean);
    setRoot(root);

  }

}
