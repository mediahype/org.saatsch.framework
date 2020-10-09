package org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table;



import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableItem;

/**
 * A selection adapter for the Table inside the {@link AbstractListEditor}.<br>
 * calls updateEditors() on the {@link AbstractListEditor} for which it works.
 * 
 * 
 * @author saatsch
 *
 */
public class TableRowSelected extends SelectionAdapter {

  private final AbstractListEditor parent;

  private TableItem lastSelected;

  public TableRowSelected(AbstractListEditor parent) {
    super();
    this.parent = parent;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    selected();
  }
  
  
  public void selected(){
    TableItem selected = parent.getTblTable().getSelection()[0];
    
    if (selected != lastSelected) {
      parent.updateEditors(parent.getTblTable().getSelectionIndex());
      lastSelected = selected;
      
    }   
  }
  
}
