package org.saatsch.framework.jmmo.data.editor.ui.dialog.textseditor;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.saatsch.framework.base.swt.SwtUtil;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

class IntlStringsTableSelected extends SelectionAdapter {

  private IntlStringsDialog client;
  
  private TableItem lastItem;

  public IntlStringsTableSelected(IntlStringsDialog textEditorWindow) {
    this.client = textEditorWindow;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    Table t = (Table) e.getSource();

    SwtUtil.selected(t).ifPresent(item -> {
      if (item != lastItem) {
        lastItem = item;
        client.setTextToEdit((IntlString) item.getData());
      }
    });
  }
}
