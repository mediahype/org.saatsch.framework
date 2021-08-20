package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.Node;
import javafx.scene.control.TabPane;

public class Tab extends javafx.scene.control.Tab {

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
  
  /**
   * adds this to the tabs of the given {@link TabPane}. Only works if this is a {@link Tab}
   * 
   * @param parent the parent {@link TabPane}.
   * @return this.
   */
  public Tab withParent(TabPane parent) {
    parent.getTabs().add((Tab) this);
    return this;
  }
  
}
