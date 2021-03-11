package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * a tab in the editor.
 */
public class EditorTabImpl extends Tab implements EditorTab {

  private final Class<?> clazz;

  /**
   * @param clazz the class this tab should handle
   */
  public EditorTabImpl(Class<?> clazz) {
    super(clazz.getSimpleName());

    this.clazz = clazz;


    SplitPane sp = new SplitPane();
    sp.setDividerPositions(0.3);

    sp.getItems().add(new LeftPane(this));
    sp.getItems().add(new Pane());

    setContent(sp);

  }

  @Override
  public Class getObjectClass() {
    return clazz;
  }
}
