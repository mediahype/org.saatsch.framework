package org.saatsch.framework.base.jfxbase;

import org.joda.beans.Bean;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

/**
 * table that displays the properties of a {@link Bean}.
 * 
 * @author saatsch
 *
 */
public class BeanTable extends TreeTableView<Object> {

  private final TreeTableColumn<Object, String> typeColumn;
  private Bean bean;

  private final boolean expandCollections;

  /**
   * Creates a new {@link BeanTable}
   *
   * @param expandCollections if collections should be expandable.
   */
  public BeanTable(boolean expandCollections) {
    this.expandCollections = expandCollections;

    TreeTableColumn<Object, String> nameColumn = new TreeTableColumn<>("Property Name");
    typeColumn = new TreeTableColumn<>("Type");
    TreeTableColumn<Object, String> valueColumn = new TreeTableColumn<>("Property Value");
    
    valueColumn.setCellValueFactory(new ValueLabelProvider());
    typeColumn.setCellValueFactory(new TypeLabelProvider());
    nameColumn.setCellValueFactory(new NameLabelProvider());

    getColumns().clear();
    
    getColumns().add(nameColumn);
    getColumns().add(typeColumn);
    getColumns().add(valueColumn);

    setShowRoot(false);

  }

  public BeanTable setBean(Bean bean){
    TreeItem<Object> root = new BeanTableItem(bean, expandCollections);
    setRoot(root);
    this.bean = bean;
    return this;
  }

  public Bean getBean() {
    return bean;
  }

  public void setColWidth(int col , int width){
    getColumns().get(col).setPrefWidth(width);
  }

  /**
   * @param displayType true if the type column should be displayed.
   * @return this.
   */
  public BeanTable displayType(boolean displayType){
    typeColumn.setVisible(displayType);
    return this;
  }

}
