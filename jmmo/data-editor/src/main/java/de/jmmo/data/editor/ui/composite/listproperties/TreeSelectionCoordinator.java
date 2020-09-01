package de.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;

public class TreeSelectionCoordinator implements ISelectionChangedListener {

  private TreeViewer other;

  /**
   * we must save the previous selection as it turns out that a selctionChanged event is raised on
   * every call to setSelction instead of only those calls that actually change the selection.
   */
  private ISelection previousSelection;


  public TreeSelectionCoordinator(TreeViewer other) {
    this.other = other;
  }

  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    if (event.getSelection().equals(previousSelection)) {
      return;
    }

    previousSelection = event.getSelection();

    other.setSelection(event.getSelection());

  }

}
