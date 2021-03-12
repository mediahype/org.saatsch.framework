package org.saatsch.framework.jmmo.data.editor.fx;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.editor.fx.tab.EditorTabImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class DataEditorFxApp extends Application {

  private static final Logger LOG = LoggerFactory.getLogger(DataEditorFxApp.class);

  private Scene scene;
  private VBox root;

  private Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception {

    try {
      this.stage = primaryStage;
      root = FXMLLoader.load(DataEditorFxApp.class.getResource("Editor.fxml"));
      scene = new Scene(root, 800, 600);
      scene.getStylesheets()
          .add(DataEditorFxApp.class.getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);

//      JMetro jMetro = new JMetro(Style.LIGHT);
//      jMetro.setScene(scene);

      fillContents();




      primaryStage.show();
    } catch (Exception e) {
      LOG.error("Error: ", e);
    }
    
  }

  private void fillContents() {
    for (Class<?> c : JmmoContext.getBean(DataConfig.class).getBaseClasses()) {
      addTab(c);
    }
  }

  private void addTab(Class<?> c) {
    TabPane tabPane = (TabPane) root.getChildren().stream().filter(n -> n instanceof TabPane).findFirst().get();
    EditorTabImpl newTab = new EditorTabImpl(c);


    tabPane.getTabs().add(newTab);



  }

}
