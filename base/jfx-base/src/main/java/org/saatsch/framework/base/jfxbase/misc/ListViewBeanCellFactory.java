package org.saatsch.framework.base.jfxbase.misc;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;

public class ListViewBeanCellFactory<T extends Bean> implements Callback<ListView<T>, ListCell<T>> {

  private final MetaProperty<?> property;

  public ListViewBeanCellFactory(MetaProperty<?> property) {
    this.property = property;
  }

  @Override
  public ListCell<T> call(ListView<T> param) {
    return new ListCell<>(){
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
