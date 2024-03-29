package org.saatsch.framework.base.jfxbase.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.saatsch.framework.base.jfxbase.control.VBox;

public abstract class AbstractDemo extends Application {
  protected Stage stage;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    VBox contentRootRegion = new VBox();
    
    Scene scene = new Scene(contentRootRegion);
    
    stage.setScene(scene);
    
    fill(contentRootRegion);
    
    stage.show();
    
  }


  /**
   * called before the stage is shown
   *
   * @param root the root
   */
  protected abstract void fill(VBox root);
  
  
}
