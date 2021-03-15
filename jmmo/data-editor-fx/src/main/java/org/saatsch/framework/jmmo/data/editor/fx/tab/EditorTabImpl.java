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
  private EditMode editMode = EditMode.EDITORS;
  private final SplitPane splitPane;



  /**
   * @param clazz the class this tab should handle
   */
  public EditorTabImpl(Class<?> clazz) {
    super(clazz.getSimpleName());

    this.clazz = clazz;

    leftPane = new LeftPane(this);
    splitPane = new SplitPane(leftPane);
    splitPane.setDividerPositions(0.3);

    RightPaneEditors rightPaneEditors = new RightPaneEditors();
    leftPane.setSelectionChangedListener(rightPaneEditors);
    splitPane.getItems().add(rightPaneEditors);

    setContent(splitPane);

  }

  @Override
  public Class getObjectClass() {
    return clazz;
  }


  /**
   * selects an object in the bean tree (the left pane)
   *
   * @param pointer the pointer to the object
   * @return true if the object was selected.
   */
  public boolean selectObject(Pointer pointer) {
    return leftPane.selectObject(pointer);
  }


  public void toggleEditMode(){
    if (editMode == EditMode.EDITORS){
      setRightPane(new RightPaneTable());
      editMode = EditMode.TABLE;
    }else{
      setRightPane(new RightPaneEditors());
      editMode = EditMode.EDITORS;
    }
  }


  private enum EditMode {
    EDITORS, TABLE
  }

  /**
   * sets the right pane to a new right pane.
   * saves and restores: (1) the current Bean selection, (2) the divider position
   *
   * @param newRightPane the new right pane.
   */
  private void setRightPane(AbstractRightPane newRightPane){
    double[] dividerPositions = splitPane.getDividerPositions();
    AbstractRightPane oldRightPane = (AbstractRightPane) splitPane.getItems().get(1);
    splitPane.getItems().remove(oldRightPane);
    newRightPane.selectionChanged(oldRightPane.getCurrentSelection());
    leftPane.setSelectionChangedListener(newRightPane);
    splitPane.getItems().add(newRightPane);
    splitPane.setDividerPositions(dividerPositions);
  }

}
