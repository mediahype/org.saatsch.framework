package org.saatsch.framework.base.jfxbase;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.joda.beans.Property;

public class TypeLabelProvider implements
    Callback<CellDataFeatures<Object, String>, ObservableValue<String>> {

  @Override
  public ObservableValue<String> call(CellDataFeatures<Object, String> param) {

    if (param.getValue().getValue() == null){
      return null;
    }

    Object value = param.getValue().getValue();

    if (value instanceof Property) {
      if (((Property) value).get() != null) {
        return new SimpleStringProperty(((Property) value).get().getClass().getSimpleName());
      }
        
    }
    
    return new SimpleStringProperty(value.getClass().getSimpleName());
  }
}
