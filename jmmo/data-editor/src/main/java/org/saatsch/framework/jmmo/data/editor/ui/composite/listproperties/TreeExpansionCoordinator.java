package org.saatsch.framework.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * helps to keep the expansion levels of two trees in sync. N.b.: This solution cannot handle
 * synchronizing more than 2 trees but this is not required atm.
 * 
 * @author saatsch
 *
 */
public class TreeExpansionCoordinator implements ITreeViewerListener {

  private TreeViewer other;

  public TreeExpansionCoordinator(TreeViewer other) {
    this.other = other;
  }

  @Override
  public void treeCollapsed(TreeExpansionEvent event) {
    other.collapseToLevel(event.getElement(), 1);
  }

  @Override
  public void treeExpanded(TreeExpansionEvent event) {
    other.expandToLevel(event.getElement(), 1);
  }

}
