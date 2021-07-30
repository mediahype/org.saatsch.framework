package org.saatsch.framework.base.jfxbase.control;

import javafx.scene.control.MenuBar;

public class Menu extends javafx.scene.control.Menu implements ExtendedControl<Menu> {

  public Menu(String text) {
    super(text);
  }

  public Menu withParent(MenuBar menuBar) {
    menuBar.getMenus().add(this);
    return this;
  }

}
