package org.saatsch.framework.base.jfxbase.widget;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.saatsch.framework.base.jfxbase.control.Button;
import org.saatsch.framework.base.jfxbase.control.VBox;

public class Loading extends Dialog {

  private Runnable task;

  ImageView image = new ImageView(
      new Image(this.getClass().getResource("Rolling-1s-200px.gif").toExternalForm()));

  public Loading() {
    setTitle("Loading ...");
    DialogPane dialogPane = getDialogPane();
    dialogPane.setContent(new VBox().withChildren(image));
  }

  public Loading withTask(Runnable r) {
    task = r;
    return this;
  }

  public void run(){
    task.run();
  }

}
