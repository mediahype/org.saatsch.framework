package de.jmmo.data.editor.ui.types.builtin.table;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.data.editor.ui.dialog.EnumSelectionDialog;

public class EnumListEditor extends AbstractListEditor {

  public EnumListEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, objectToEdit);
    repaintTable();
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

  @Override
  public void updateEditors(int selectionIndex) {

  }

  @Override
  protected void btnAddPressed(SelectionEvent e) {
    EnumSelectionDialog dia = new EnumSelectionDialog(getShell(), property);
    Object object = dia.open();
    addTo(object, property);
    saveObject();
    repaintTable();
  }

  private void addTo(Object toAdd, Property<Object> property) {
    if (null == toAdd) {
      return;
    }

    ((Collection) property.get()).add(toAdd);

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


}
