package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;

/**
 * a tab in the editor. It knows which class it should handle.
 */
public class EditorTabImpl extends Tab implements EditorTab, SelectionChanged<Bean> {

  private final Class<?> clazz;
  private final LeftPane cmpLeft;
  private EditMode editMode = EditMode.EDITORS;
  private final SplitPane splitPane;
  private boolean inspectMode = false;

  private AbstractRightPane currentRightPane;


  /**
   * @param clazz the class this tab should handle
   */
  public EditorTabImpl(Class<?> clazz) {
    super(clazz.getSimpleName());

    this.clazz = clazz;

    cmpLeft = new LeftPane(this);
    splitPane = new SplitPane(cmpLeft);
    splitPane.setDividerPositions(0.3);

    RightPaneEditors rightPaneEditors = new RightPaneEditors();
    currentRightPane = rightPaneEditors;

    cmpLeft.setListener(this);

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
    return cmpLeft.selectObject(pointer);
  }


  public void toggleEditMode(){
    if (!cmpLeft.hasSelection()) return;

    if (editMode == EditMode.EDITORS){
      setRightPane(new RightPaneTable());
      editMode = EditMode.TABLE;
    }else{
      setRightPane(new RightPaneEditors());
      editMode = EditMode.EDITORS;
    }
  }

  @Override
  public void reload() {
    cmpLeft.reload();
  }

  @Override
  public void toggleInspect() {
    inspectMode = !inspectMode;
    drawInspect();
  }

  private void drawInspect() {
    cmpLeft.setInspectVisible(inspectMode);
    if (inspectMode) {
      cmpLeft.inspect();
    }
  }

  @Override
  public void selectionChanged(Bean newSelection) {
    currentRightPane.selectionChanged(newSelection);
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
    this.currentRightPane = newRightPane;
    double[] dividerPositions = splitPane.getDividerPositions();
    AbstractRightPane oldRightPane = (AbstractRightPane) splitPane.getItems().get(1);
    splitPane.getItems().remove(oldRightPane);
    newRightPane.selectionChanged(oldRightPane.getCurrentSelection());
    splitPane.getItems().add(newRightPane);
    splitPane.setDividerPositions(dividerPositions);
  }

}
