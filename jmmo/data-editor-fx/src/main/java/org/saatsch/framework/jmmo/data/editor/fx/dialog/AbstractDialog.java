package org.saatsch.framework.jmmo.data.editor.fx.dialog;


import javafx.scene.control.Dialog;
import javafx.stage.Window;

public abstract class AbstractDialog<T> extends Dialog<T> {

  public AbstractDialog(){
    Window window = getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> close());
  }

}
