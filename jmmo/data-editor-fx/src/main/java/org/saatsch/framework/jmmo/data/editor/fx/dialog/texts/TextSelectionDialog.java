package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.scene.control.ButtonType;
import org.saatsch.framework.base.jfxbase.control.Dialog;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

import java.util.Optional;

/**
 * used to select an {@link IntlString}
 */
public class TextSelectionDialog extends Dialog<IntlString> {

  private final TextSelection content = new TextSelection(Optional.empty());

  public TextSelectionDialog() {
    super();
    setTitle("Select Text");
    withContent(content).withButtons(ButtonType.OK, ButtonType.CANCEL);

    setResultConverter(button -> {
      if (button == ButtonType.OK && content.getSelected().isPresent()) {
        return content.getSelected().get();
      }
      return null;
    });

    content.setPrefWidth(600);

  }
}
