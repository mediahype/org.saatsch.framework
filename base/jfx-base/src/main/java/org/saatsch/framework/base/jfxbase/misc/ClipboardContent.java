package org.saatsch.framework.base.jfxbase.misc;

public class ClipboardContent extends javafx.scene.input.ClipboardContent {

  public ClipboardContent withString(String s){
    putString(s);
    return this;
  }

}
