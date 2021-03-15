package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import org.saatsch.framework.jmmo.data.api.Pointer;

/**
 * a tab in the editor. It knows which class it should handle.
 */
public class EditorTabImpl extends Tab implements EditorTab {

  private final Class<?> clazz;
  private final LeftPane leftPane;

  /**
   * @param clazz the class this tab should handle
   */
  public EditorTabImpl(Class<?> clazz) {
    super(clazz.getSimpleName());

    this.clazz = clazz;

    leftPane = new LeftPane(this);
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

  /**
   * selects an object in the bean tree
   *
   * @param pointer the pointer to the object
   * @return true if the object was selected.
   */
  public boolean selectObject(Pointer pointer) {
    return leftPane.selectObject(pointer);
  }

}
