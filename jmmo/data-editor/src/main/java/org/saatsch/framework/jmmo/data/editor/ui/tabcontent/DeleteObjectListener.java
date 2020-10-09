package org.saatsch.framework.jmmo.data.editor.ui.tabcontent;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.data.action.DeleteObjectAction;
import org.saatsch.framework.base.swt.MessageBoxUtil;

public class DeleteObjectListener extends SelectionAdapter {

  private final Class oc;

  private final EditorTabContent parent;

  private static final Logger LOG = LoggerFactory.getLogger(DeleteObjectListener.class);

  public DeleteObjectListener(Class oc, EditorTabContent parent) {
    super();
    this.oc = oc;
    this.parent = parent;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {

    LOG.debug("Delete Object selected ...");

    if (null == parent.getCurrentlySelected()) {
      MessageBoxUtil.showErrorMessage("Please select an Object", parent.getShell());
      return;
    }

    DeleteObjectAction a = new DeleteObjectAction(parent.getCurrentlySelected());
    a.execute();
    
    parent.repaintAll();

  }

}
