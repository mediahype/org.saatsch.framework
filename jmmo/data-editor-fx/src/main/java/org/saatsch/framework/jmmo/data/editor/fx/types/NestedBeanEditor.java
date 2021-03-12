package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class NestedBeanEditor extends  AbstractEditor {

  String cssLayout = "-fx-border-color: black;\n" +
      "-fx-border-insets: 2;\n" +
      "-fx-border-width: 1;\n" +
      "-fx-border-style: dashed;\n";

  private HBox content;

  public NestedBeanEditor(Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);



    content = new HBox();
    content.setStyle(cssLayout);
    content.setPrefWidth(500);

    getChildren().add(content);



    fillContents();

  }

  private void fillContents() {

    Bean nestedBean = (Bean) property.get();

    EditorCreator.createEditors(content, nestedBean, objectToEdit);

  }
}
