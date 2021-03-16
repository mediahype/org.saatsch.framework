package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.scene.control.SplitPane;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.AbstractDialog;

public class TextWindow extends AbstractDialog<Void> {

  public TextWindow() {
    super("Texts");

    TextSelection textSelection = new TextSelection();

    TextEditor textEditor = new TextEditor();

    SplitPane splitPane = new SplitPane(textSelection,textEditor);


    content.getChildren().add(splitPane);

  }
}
