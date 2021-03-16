package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.api.DataConfig;

public class TextSelection extends VBox {

  private Lazy<DataConfig> cfg =  Lazy.of(() -> JmmoContext.getBean(DataConfig.class));

  public TextSelection() {

    HBox language = new HBox();
    language.getChildren().add(new Label("Language"));
    language.getChildren().add(new ComboBox<>());
    getChildren().add(language);


    HBox search = new HBox();
    search.getChildren().add(new Label("Search:"));
    search.getChildren().add(new TextField());
    getChildren().add(search);

    TableView<Object> tblTexts = new TableView<>();
    tblTexts.getColumns().add(new TableColumn<>("Text"));
    tblTexts.getColumns().add(new TableColumn<>("ID"));
    tblTexts.getColumns().add(new TableColumn<>("Translations"));
    getChildren().add(tblTexts);

  }
}
