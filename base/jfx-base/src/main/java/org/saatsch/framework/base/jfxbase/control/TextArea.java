package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.text.Font;

public class TextArea extends javafx.scene.control.TextArea implements ExtendedNode<TextArea> {

  public TextArea withMonospace(){
    setFont(Font.font("monospace"));
    return this;
  }

}
