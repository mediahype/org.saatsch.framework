package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.scene.control.SplitPane;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.model.IntlString;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.AbstractDialog;

import java.util.List;

public class TextWindow extends AbstractDialog<Void> {

  public TextWindow() {
    super("Texts");

    TextSelection textSelection = new TextSelection();
    TextEditor textEditor = new TextEditor();
    SplitPane splitPane = new SplitPane(textSelection,textEditor);
    content.withStretchedChild(splitPane);

    setResizable(true);

    List<IntlString> intlStrings = JmmoContext.getBean(IntlStringService.class).loadAll();

    textSelection.setStrings(intlStrings);

  }
}
