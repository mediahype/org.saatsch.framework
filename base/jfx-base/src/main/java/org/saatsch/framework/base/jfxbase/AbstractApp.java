package org.saatsch.framework.base.jfxbase;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class AbstractApp extends Application {

  private VBox contentRootRegion;
  
  
  @Override
  public void start(Stage stage) {
    
    contentRootRegion = new VBox();
  //  root.getChildren().add(contentRootRegion);

    
    stage.show();
  }
  
  public void showComponent(Class componentClass) {
    contentRootRegion.getChildren().clear();
    contentRootRegion.getChildren().add(JfxUtil.load(componentClass));

  }
  
  public abstract void createView();

  
}
