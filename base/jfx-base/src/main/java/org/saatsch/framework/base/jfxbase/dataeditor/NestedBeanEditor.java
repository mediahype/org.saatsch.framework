package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class NestedBeanEditor extends  AbstractEditor {

  private FlowPane content;

  public NestedBeanEditor(Pane parent, Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);

    content = new FlowPane();
    content.setStyle(Styles.nestedBeanEditor);

    setPrefWidth(parent.getWidth());

    getChildren().add(content);

    parent.widthProperty().addListener(
        (observable, oldValue, newValue) -> prefWidthProperty().setValue(newValue));

    fillContents();

  }

  private void fillContents() {

    Bean nestedBean = (Bean) property.get();

    EditorCreator.createEditors(content, nestedBean, objectToEdit);

  }
}
