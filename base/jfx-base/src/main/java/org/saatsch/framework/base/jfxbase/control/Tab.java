package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;

public class Tab extends javafx.scene.control.Tab implements ExtendedControl<Tab>{

  public Tab() {
    super();
    setClosable(false);
  }

  public Tab(String text, Node content) {
    super(text, content);
    setClosable(false);
  }

  public Tab(String text) {
    super(text);
    setClosable(false);
  }

  public Tab withContent(Node node) {
    setContent(node);
    return this;
  }
  
}
