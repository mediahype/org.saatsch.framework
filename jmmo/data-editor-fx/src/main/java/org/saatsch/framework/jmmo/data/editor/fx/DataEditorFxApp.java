package org.saatsch.framework.jmmo.data.editor.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.editor.fx.base.KeyCombinations;
import org.saatsch.framework.jmmo.data.editor.fx.tab.EditorTab;
import org.saatsch.framework.jmmo.data.editor.fx.tab.EditorTabImpl;
import org.saatsch.framework.jmmo.data.editor.fx.tab.EditorTabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEditorFxApp extends Application {

  private static final Logger LOG = LoggerFactory.getLogger(DataEditorFxApp.class);

  private VBox root;

  @Override
  public void start(Stage primaryStage) throws Exception {

    try {
      root = FXMLLoader.load(DataEditorFxApp.class.getResource("Editor.fxml"));
      Scene scene = new Scene(root, 800, 600);
      scene.getStylesheets()
          .add(DataEditorFxApp.class.getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);

      new KeyCombinations(this, scene);

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
    getEditorTabPane().getTabs().add(new EditorTabImpl(c));
  }

  /**
   * @return the {@link EditorTabPane}
   */
  private EditorTabPane getEditorTabPane(){
    return getFirst(EditorTabPane.class);
  }

  private <T> T getFirst(Class<T> clazz) {
    return (T) root.getChildren().stream().filter(n ->  clazz.isAssignableFrom(n.getClass())).findFirst().get();
  }

  /**
   * @return the currently active {@link EditorTab}
   */
  public EditorTab getActiveTab(){
    return (EditorTab) getEditorTabPane().getSelectionModel().getSelectedItem();
  }

  public MenuBar getMenu(){
    return getFirst(MenuBar.class);
  }

}
