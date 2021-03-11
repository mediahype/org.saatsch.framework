package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {

  public LeftPane(EditorTab parent ){

    ListView<Object> objectListView = new ListView<>();

    VBox.setVgrow(objectListView, Priority.ALWAYS);

    getChildren().add(new TextField());
    getChildren().add(objectListView);

  }

}
