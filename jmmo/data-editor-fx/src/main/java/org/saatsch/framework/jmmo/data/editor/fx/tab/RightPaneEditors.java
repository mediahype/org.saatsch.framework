package org.saatsch.framework.jmmo.data.editor.fx.tab;


import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.types.EditorCreator;

/**
 * container for the field editors that are displayed on the right side of a tab.
 */
public class RightPaneEditors extends ScrollPane implements SelectionChanged<Bean> {

  private FlowPane flowPane;

  public RightPaneEditors() {
    super();
    setFitToWidth(true);
    setFitToHeight(true);

    // clear();
    flowPane = new FlowPane(Orientation.HORIZONTAL);
    for (int i = 0; i < 100; i++) {
      flowPane.getChildren().add(new Label("Label" + i));

    }
    getChildren().add(flowPane);
    
    // layout();
  }


  private void clear() {
    getChildren().clear();
    flowPane = new FlowPane(Orientation.HORIZONTAL);
    getChildren().add(flowPane);
  }

  private void fill(Bean bean) {
    EditorCreator.createEditors(flowPane, bean, bean);
    layout();
  }

  @Override
  public void selectionChanged(Bean newSelection) {
    clear();
    fill(newSelection);
  }
}
