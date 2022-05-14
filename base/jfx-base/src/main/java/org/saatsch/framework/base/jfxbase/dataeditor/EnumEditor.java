package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class EnumEditor extends AbstractEditor{

  private final Enum enumToEdit;

  private final ComboBox<String> cmbList;

  public EnumEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);
    enumToEdit = (Enum) property.get();

    cmbList = new ComboBox();
    cmbList.setEditable(false);
    getChildren().add(cmbList);

    fillContents();
  }

  private void fillContents() {

    ObservableList<String> objects = FXCollections.observableArrayList();
    objects.addAll(PropertyUtil.getEnumConstantsList(property));
    cmbList.setItems(objects);

    cmbList.getSelectionModel().select(property.get().toString());

    if (!property.metaProperty().style().isWritable()) {
      cmbList.setDisable(true);
    }

    cmbList.valueProperty().addListener((observable, oldValue, newValue) -> {
      property.set(PropertyUtil.getEnumInstance(newValue, enumToEdit.getDeclaringClass()));
      saveObject();
    });

  }
}
