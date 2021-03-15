package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.editor.fx.types.PointerEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditorTabPane extends TabPane {

  private static final Logger LOG = LoggerFactory.getLogger(EditorTabPane.class);


  public void selectObject(Pointer pointer) {

    EditorTabImpl tab1 = (EditorTabImpl) getTabs().stream()
        .filter(tab -> ((EditorTabImpl) tab).getObjectClass().equals(pointer.getBaseClass()))
        .findFirst().get();

    if (tab1 != null && tab1.selectObject(pointer)){
      getSelectionModel().select(tab1);
    }





  }



}
