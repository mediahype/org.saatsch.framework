package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class BoolEditor extends AbstractEditor {

  private final CheckBox check;

  public BoolEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    check = new CheckBox();

    check.setSelected((Boolean) property.get());

    getChildren().add(check);

    check.selectedProperty().addListener((observable, oldValue, newValue) -> {
      property.set(newValue);
      saveObject();
    });


  }
}
