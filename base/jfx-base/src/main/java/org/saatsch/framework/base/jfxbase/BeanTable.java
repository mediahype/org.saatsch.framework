package org.saatsch.framework.base.jfxbase;

import org.joda.beans.Bean;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.util.ArrayList;
import java.util.List;

/**
 * table that displays a bean.
 * 
 * @author saatsch
 *
 */
public class BeanTable extends TreeTableView<Object> {

  private TreeItem<Object> root;



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

    // informational functionality
//    addEventHandler(MouseEvent.MOUSE_CLICKED, click -> {
//      if (click.getClickCount() == 2) {
//        
//        Object value = getSelectionModel().getSelectedItem().getValue();
//        if (value instanceof Property) {
//          Object object = ((Property<?>) value).get();
//          if (object instanceof Binding) {
//            ((Binding<?>) object).getDependencies().stream().forEach(dep -> {
//              LOG.info("Dependency: {}" , dep);                        
//            });
//          }
//        }
//        
//      }
//    });
  }

  public void setBean(Bean bean){
    root = new BeanTableItem(bean);
    setRoot(root);
  }

  public void  setColWidth(int col , int width){
    getColumns().get(col).setPrefWidth(width);
  }

}
