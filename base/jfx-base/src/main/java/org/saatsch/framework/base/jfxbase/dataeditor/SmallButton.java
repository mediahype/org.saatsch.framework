package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.Node;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import org.saatsch.framework.base.jfxbase.control.Button;

public class SmallButton extends Button {

  public static final int size = 16;

  public SmallButton(String text) {
    super();
    setPrefSize(size, size);
    setMinSize(size, size);
    setTooltip(new Tooltip(text));
    setTextOverrun(OverrunStyle.CLIP);
    setStyle("-fx-padding: 0 0 0 0;");
  }

  public SmallButton withGraphic(Node value){
    setGraphic(value);
    return this;
  }

}
