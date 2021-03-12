package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * a tab in the editor. It knows which class it should handle.
 */
public class EditorTabImpl extends Tab implements EditorTab {

  private final Class<?> clazz;

  /**
   * @param clazz the class this tab should handle
   */
  public EditorTabImpl(Class<?> clazz) {
    super(clazz.getSimpleName());

    this.clazz = clazz;

    LeftPane leftPane = new LeftPane(this);
    RightPaneEditors rightPane = new RightPaneEditors();

    leftPane.setSelectionChangedListener(rightPane);

    SplitPane sp = new SplitPane(leftPane,rightPane);
    sp.setDividerPositions(0.3);

    setContent(sp);

  }

  @Override
  public Class getObjectClass() {
    return clazz;
  }
}
