package org.saatsch.framework.base.jfxbase.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class AbstractDemo extends Application {

  @Override
  public void start(Stage stage) throws Exception {

    VBox contentRootRegion = new VBox();
    
    Scene scene = new Scene(contentRootRegion);
    
    stage.setScene(scene);
    
    fill(contentRootRegion);
    
    stage.show();
    
  }

  
  protected abstract void fill(VBox root);
  
  
}
