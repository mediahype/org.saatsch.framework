package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.saatsch.framework.base.jfxbase.control.Button;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

/**
 * edits a single Text Entry.
 */
public class TextEditor extends VBox {

  private final TextArea txtEntry= new TextArea();
  private final TextWindow client;
  private IntlString currentString;

  private Lazy<DataSink> data =  Lazy.of(() -> JmmoContext.getBean(DataSink.class));
  private Lazy<DataConfig> cfg =  Lazy.of(() -> JmmoContext.getBean(DataConfig.class));

  public TextEditor(TextWindow client) {
    this.client = client;
    getChildren().add(new Label("Text Entry: "));
    getChildren().add(txtEntry);
    getChildren().add(new Button("Save").withAction(this::save));

  }

  private void save(ActionEvent actionEvent) {
    currentString.setForLanguage(cfg.get().getCurrentLanguage(), txtEntry.getText());
    data.get().save(currentString);
    client.refreshTable();
  }

  public void setTextToEdit(IntlString data) {
    this.currentString = data;
    updateText();
  }

  private void updateText() {
    if (null != currentString) {
      txtEntry.setText(currentString.getForLanguage(cfg.get().getCurrentLanguage()));
    }
  }

  void languageChanged() {
    updateText();
  }

}
