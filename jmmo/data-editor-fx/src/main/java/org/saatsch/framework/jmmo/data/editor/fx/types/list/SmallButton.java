package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import javafx.scene.Node;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import org.saatsch.framework.base.jfxbase.control.Button;

public class SmallButton extends Button {

  public SmallButton(String text) {
    super(text);
    setPrefSize(15, 15);
    setMinSize(15, 15);
    setTooltip(new Tooltip(text));
    setTextOverrun(OverrunStyle.CLIP);
    setStyle("-fx-padding: 0 0 0 0;");
  }

  public Button withGraphic(Node value){
    setGraphic(value);
    return this;
  }

}
