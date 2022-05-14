package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.control.Button;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class ImageEditor extends AbstractEditor {

  private Button btnSelect;

  public ImageEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    btnSelect = new Button("Select");

    getChildren().add(btnSelect);

  }
}
