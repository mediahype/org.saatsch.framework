package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.joda.beans.Bean;

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

    setShowRoot(false);


  }

  public void setBean(Bean bean){
    this.bean = bean;

    root = new BeanTableBeanItem(bean);
    setRoot(root);

  }

}
