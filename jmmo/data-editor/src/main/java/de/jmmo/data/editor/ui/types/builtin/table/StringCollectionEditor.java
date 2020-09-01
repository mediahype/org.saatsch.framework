package de.jmmo.data.editor.ui.types.builtin.table;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.data.editor.ui.dialog.AddStringDialog;

public class StringCollectionEditor extends AbstractListEditor {

  public StringCollectionEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, objectToEdit);
    repaintTable();
  }

  @Override
  public void updateEditors(int selectionIndex) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void btnAddPressed(SelectionEvent e) {
    AddStringDialog diag = new AddStringDialog(getShell());
    String string = diag.open();
    
    ((Collection<String>)property.get()).add(string);
    repaintTable();
  }

  @Override
  protected void btnRemovedPressed(SelectionEvent e) {
    if (tblTable.getSelectionCount() > 0) {
      TableItem itm = tblTable.getSelection()[0];
      Object object = itm.getData();
      ((Collection) property.get()).remove(object);
      saveObject();
      repaintTable();
    }
    
  }

  @Override
  protected void repaintTable() {
    tblTable.removeAll();
    for (Object o : collectionToEdit) {

      TableItem ti = new TableItem(tblTable, SWT.NONE);
      
      
      
      ti.setText(o.toString());
      ti.setData(o);

    }

    
  } 

}
