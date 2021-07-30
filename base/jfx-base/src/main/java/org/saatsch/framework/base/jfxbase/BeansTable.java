package org.saatsch.framework.base.jfxbase;

import org.joda.beans.Bean;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class BeansTable extends TreeTableView<Object>{

  private TreeItem<Object> root;
  
  public BeansTable() {
    TreeTableColumn<Object, String> nameColumn = new TreeTableColumn<>("Property Name");
    TreeTableColumn<Object, String> typeColumn = new TreeTableColumn<>("Type");

    nameColumn.setCellValueFactory(new NameLabelProvider());
    typeColumn.setCellValueFactory(new TypeLabelProvider());
    
    getColumns().clear();
    
    getColumns().add(nameColumn);
    getColumns().add(typeColumn);
   
    setShowRoot(false);
    
  }
  
  
  public void setBean(Bean bean){
    root = new BeanTableItem(bean);
    setRoot(root);
  }
  
}
