package org.saatsch.framework.base.jfxbase;

import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class ValueLabelProvider implements
    Callback<CellDataFeatures<Object, String>, ObservableValue<String>> {

  @Override
  public ObservableValue<String> call(CellDataFeatures<Object, String> param) {

    if (param.getValue().getValue() == null){
      return null;
    }

    Object value = param.getValue().getValue();

    if (value instanceof Property){

      Property<Object> p = (Property) value;

      if (p.get() instanceof Bean) {
        return new SimpleStringProperty( buildForBean(p));
      } else {
        return new SimpleStringProperty(p.get().toString());
      }

    }

    return null;

  }


  private String buildForBean(Property<Object> p) {
    StringBuilder buffer = new StringBuilder();
    buildPropertyRepresentation(buffer, p);
    return buffer.toString();
  }


  private void buildPropertyRepresentation(StringBuilder buffer, Property<Object> property) {
    buffer.append("(");
    Object object = property.get();
    if (object instanceof Bean) {
      handleBeanProperty(buffer, (Bean) object);
    }  else {
      buffer.append(object);
    }
    buffer.append(")");
  }

  private void handleBeanProperty(StringBuilder buffer, Bean object) {

    List<Property<Object>> collect = object.propertyNames().stream()
        .map(pName -> object.property(pName)).collect(Collectors.toList());

    for (Property p : collect) {
      buildPropertyRepresentation(buffer, p);
    }

  }
}
