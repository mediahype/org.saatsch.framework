package org.saatsch.framework.jmmo.data.editor.ui.tabcontent;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.joda.beans.Bean;

import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.TabContentCallback;

/**
 * Draws the content of the editor when the selection has changed.
 * 
 * @author saatsch
 * 
 */
public class ObjectSelectionAdapter implements ISelectionChangedListener {

  private Object previousSelection;

  private TabContentCallback client;


  public ObjectSelectionAdapter(TabContentCallback parent) {
    this.client = parent;

  }


  @Override
  public void selectionChanged(SelectionChangedEvent event) {

    Object currentlySelected = client.getCurrentlySelected();

    // only do something if selection has really changed
    if (currentlySelected != previousSelection) {
      previousSelection = currentlySelected;
    } else {
      return;
    }

    client.contentDisposeChildren();

    // we can only operate on Beans
    if (!(currentlySelected instanceof Bean)) {
      return;
    }
    Bean bean = (Bean) currentlySelected;

    client.repaintContent(bean);


  }



}
