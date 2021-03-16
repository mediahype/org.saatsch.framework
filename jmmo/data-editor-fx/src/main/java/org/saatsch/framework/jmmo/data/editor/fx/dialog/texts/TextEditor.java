package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class TextEditor extends VBox {

  public TextEditor() {

    getChildren().add(new Label("Text Entry: "));
    getChildren().add(new TextArea());
    getChildren().add(new Button("Save"));

  }
}
