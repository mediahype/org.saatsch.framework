package org.saatsch.framework.base.jfxbase;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class JfxUtil {

  
  /**
   * loads a component. Naming rules apply. The given component must be a VBox.
   * uses FXMLLoader.
   * 
   * @param componentClass
   * @param contentRootRegion
   * @return
   * 
   * @throws RuntimeException if loading failed.
   * 
   */
  public static VBox load(Class<VBox> componentClass) {
    
    URL resource = componentClass.getResource(componentClass.getSimpleName() + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(resource);
    
    
    try {
      return fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

  }
  
}
