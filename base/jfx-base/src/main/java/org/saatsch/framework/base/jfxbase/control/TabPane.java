package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.control.Tab;

import java.util.List;

public class TabPane extends javafx.scene.control.TabPane implements ExtendedNode<TabPane> {

  public TabPane withChildren(List<javafx.scene.control.Tab> children){
    getTabs().addAll(children);
    return this;
  }

  public TabPane withChildren(Tab child){
    getTabs().add(child);
    return this;
  }

}
