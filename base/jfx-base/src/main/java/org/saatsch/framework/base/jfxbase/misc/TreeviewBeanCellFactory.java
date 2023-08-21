package org.saatsch.framework.base.jfxbase.misc;

import javafx.scene.control.ListCell;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;

public class TreeviewBeanCellFactory <T extends Bean> implements Callback<TreeView<T>, TreeCell<T>> {

  private final MetaProperty<?> property;

  public TreeviewBeanCellFactory(MetaProperty<?> property) {
    this.property = property;
  }


  @Override
  public TreeCell<T> call(TreeView<T> param) {
    return new TreeCell<>(){
      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          setText(item.property(property.name()).get().toString());
        }
      }
    };
  }



}
