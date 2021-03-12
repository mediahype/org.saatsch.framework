package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.Label;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class PointerEditor extends  AbstractEditor{

  public PointerEditor(Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);

    getChildren().add(new Label("TODO: "+getClass().getSimpleName()+" prop: " + property.name()));

  }
}
