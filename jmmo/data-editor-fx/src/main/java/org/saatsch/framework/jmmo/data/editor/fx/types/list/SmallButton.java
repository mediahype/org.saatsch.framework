package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import javafx.scene.control.Button;
import javafx.scene.control.OverrunStyle;

public class SmallButton extends Button {

  public SmallButton(String text) {
    super(text);
    setPrefSize(15, 15);
    setTextOverrun(OverrunStyle.CLIP);
  }
}
