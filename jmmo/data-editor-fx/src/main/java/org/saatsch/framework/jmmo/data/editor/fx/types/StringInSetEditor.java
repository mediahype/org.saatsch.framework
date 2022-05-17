package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.TableView;
import org.saatsch.framework.base.jfxbase.control.TextField;
import org.saatsch.framework.base.jfxbase.dataeditor.Repaintable;

import java.util.Set;

public class StringInSetEditor extends TextField {

  public StringInSetEditor(Set<String> collectionToEdit, Repaintable parent, TableView<String> tblTable) {

    focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == false){
        collectionToEdit.remove(tblTable.getSelectionModel().getSelectedItem());
        collectionToEdit.add(textProperty().get());
        parent.repaint();
      }
    });

    setText(tblTable.getSelectionModel().getSelectedItem());

  }



}
