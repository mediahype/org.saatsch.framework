package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class StringEditor extends AbstractEditor {

  private TextInputControl txtContent;

  public StringEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    txtContent = new TextField();
    txtContent.setText((String) property.get());

    getChildren().add(txtContent);

    txtContent.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == false) {
        property.set(txtContent.textProperty().get());
        saveObject();
      }
    });

  }
}
