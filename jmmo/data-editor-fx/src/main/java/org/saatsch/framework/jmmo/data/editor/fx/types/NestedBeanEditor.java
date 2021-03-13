package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.editor.fx.Styles;

public class NestedBeanEditor extends  AbstractEditor {

  private FlowPane content;

  public NestedBeanEditor(Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);



    content = new FlowPane();
    content.setStyle(Styles.nestedBeanEditor);


    getChildren().add(content);



    fillContents();

  }

  private void fillContents() {

    Bean nestedBean = (Bean) property.get();

    EditorCreator.createEditors(content, nestedBean, objectToEdit);

  }
}
