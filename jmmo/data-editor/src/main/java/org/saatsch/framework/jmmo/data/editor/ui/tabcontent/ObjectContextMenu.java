package org.saatsch.framework.jmmo.data.editor.ui.tabcontent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class ObjectContextMenu extends Menu {

  public ObjectContextMenu(Decorations parent, EditorTabContent tabContent) {
    super(parent, SWT.POP_UP);

    Class c = tabContent.getObjectClass();
    
    MenuItem item = new MenuItem(this, SWT.PUSH);
    item.setText("Add " + c.getSimpleName() + "...");
    item.addSelectionListener(new AddObjectListener(c, tabContent));

    item = new MenuItem(this, SWT.PUSH);
    item.setText("Delete " + c.getSimpleName() + "...");
    item.addSelectionListener(new DeleteObjectListener(c, tabContent));

    item = new MenuItem(this, SWT.PUSH);
    item.setText("Edit " + c.getSimpleName() + "...");
    item.addSelectionListener(new EditObjectListener(c, tabContent));

    item = new MenuItem(this, SWT.PUSH);
    item.setText("Duplicate " + c.getSimpleName());
    item.addSelectionListener(new DuplicateObjectListener(c, tabContent));


  }

  @Override
  protected void checkSubclass() {
    // disable subclass check
  }

}
