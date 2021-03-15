package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class StringEditor extends AbstractEditor {

  private TextInputControl txtContent;

  public StringEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    if (PropertyUtil.isLongString(property)) {
      txtContent = new TextArea();
    } else {
      txtContent = new TextField();

    }

    txtContent.setText((String) property.get());

    getChildren().add(txtContent);

    txtContent.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == false){
        property.set(txtContent.textProperty().get());
        saveObject();
      }
    });

  }
}
