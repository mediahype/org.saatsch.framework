package org.saatsch.framework.jmmo.data.editor.fx.dialog;


import javafx.scene.Node;
import javafx.stage.Window;
import org.saatsch.framework.base.jfxbase.control.Dialog;
import org.saatsch.framework.base.jfxbase.control.VBox;

public abstract class AbstractDialog<T> extends Dialog<T> {

  protected final VBox content;

  public AbstractDialog(String title){
    Window window = getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> close());
    setTitle(title);

    content = new VBox();
    getDialogPane().setContent(content);


  }

  public AbstractDialog<T> withChildren(Node... children){
    content.withChildren(children);
    return this;
  }

}
