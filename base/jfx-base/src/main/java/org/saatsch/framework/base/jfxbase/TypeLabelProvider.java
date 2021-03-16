package org.saatsch.framework.base.jfxbase;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.joda.beans.Property;

public class TypeLabelProvider implements
    Callback<CellDataFeatures<Object, String>, ObservableValue<String>> {

  private static final SimpleStringProperty NULL = new SimpleStringProperty("null");
  
  @Override
  public ObservableValue<String> call(CellDataFeatures<Object, String> param) {


    
    if (param.getValue().getValue() == null){
      return new SimpleStringProperty("val.getVal was null");
    }

    Object value = param.getValue().getValue();

    if (value instanceof Property) {
      if (((Property) value).get() != null) {
        Object valueGet = ((Property<?>) value).get();
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty(valueGet.getClass().getSimpleName());
        if (!simpleStringProperty.get().isBlank()) {
          return simpleStringProperty;          
        }
        return new SimpleStringProperty(valueGet.getClass().toGenericString());
         
      } else {
        return NULL;
      }
        
    }
    
    return new SimpleStringProperty(value.getClass().toGenericString());
  }
}
