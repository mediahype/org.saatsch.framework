package org.saatsch.framework.jmmo.data.editor.fx.types;

import org.saatsch.framework.base.jfxbase.control.TextField;
import org.saatsch.framework.base.jfxbase.dataeditor.Repaintable;

import java.util.List;

public class StringInListEditor extends TextField {

  public StringInListEditor(List<String> collectionToEdit, int index, Repaintable parent) {

    textProperty().addListener((observable, oldValue, newValue) -> {
      collectionToEdit.set(index,textProperty().get());
      parent.repaint();
    });

    setText(collectionToEdit.get(index));

  }
}
