package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class EnumEditor extends AbstractEditor{

  private final Enum enumToEdit;

  private final ComboBox<String> cmbList;

  public EnumEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    cmbList = new ComboBox();
    cmbList.setEditable(false);


    if (!property.metaProperty().style().isWritable()) {
      cmbList.setDisable(true);
    }

    enumToEdit = (Enum) property.get();

    getChildren().add(cmbList);

    fillContents();
  }

  private void fillContents() {

    ObservableList<String> objects = FXCollections.observableArrayList();
    objects.addAll(PropertyUtil.getEnumConstantsList(property));
    cmbList.setItems(objects);

    cmbList.getSelectionModel().select(property.get().toString());

  }
}
