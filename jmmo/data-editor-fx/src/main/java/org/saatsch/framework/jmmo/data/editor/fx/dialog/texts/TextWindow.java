package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.scene.control.SplitPane;
import org.saatsch.framework.jmmo.data.api.model.IntlString;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.AbstractDialog;

import java.util.Optional;

public class TextWindow extends AbstractDialog<Void> {

  private final TextSelection textSelection = new TextSelection(Optional.of(this));
  private final TextEditor textEditor = new TextEditor(this);

  public TextWindow() {
    super("Texts");

    SplitPane splitPane = new SplitPane(textSelection, textEditor);
    content.withStretchedChild(splitPane);

    setResizable(true);

  }

  void setTextToEdit(IntlString data) {
    textEditor.setTextToEdit(data);
  }

  void refreshTable() {
    textSelection.refreshTable();
  }

  void languageChanged() {
    textEditor.languageChanged();
  }
}
