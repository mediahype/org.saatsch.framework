package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.joda.beans.Bean;

public class EditorCreator {


  public static void createEditors(FlowPane flowPane, Bean toEdit, Bean toSave){

    for (String prop : toEdit.propertyNames()) {

      flowPane.getChildren().add(new Label(prop));


    }

  }


}
