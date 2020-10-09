package org.saatsch.framework.jmmo.tools.apiclient.ui.expressions;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class SelectionChanged implements ISelectionChangedListener {

  private ExpressionsUI parent;
  
  private Object previousSelection;

  
  public SelectionChanged(ExpressionsUI parent) {
    this.parent = parent;
  }
  
  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    Object currentlySelected = parent.getTree().getStructuredSelection().getFirstElement();
    
    // only do something if selection has really changed
    if (currentlySelected != previousSelection) {
      previousSelection = currentlySelected;
    } else {
      return;
    }
    
    parent.updateText();
    
    
  }

}
