package org.saatsch.framework.jmmo.data.editor.fx.dialog;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.editor.fx.types.EditorCreator;

public class EditPropertyDialog extends AbstractDialog<Void> {

  public EditPropertyDialog(Property<Object> property, Object toSave) {
    super(property.name());
    
    content.setPrefWidth(600);
    content.setPrefHeight(600);
    setResizable(true);

    
    EditorCreator.createEditorForField(content, property, (Bean) toSave);



  }
}
