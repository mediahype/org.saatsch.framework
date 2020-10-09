package org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PointerFactory;
import org.saatsch.framework.jmmo.data.editor.ui.dialog.ReferenceTargetSelectionDialog;

/**
 * edits a list of object references. This editor is used if the property to edit is a List of Pointers
 * 
 * @author saatsch
 *
 */
public class PointerListEditor extends AbstractListEditor {



  public PointerListEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, objectToEdit);
    tblTable.setHeaderVisible(false);
    repaintTable();
  }

  @Override
  protected void btnAddPressed(SelectionEvent e) {
    ReferenceTargetSelectionDialog dia = new ReferenceTargetSelectionDialog(getShell(), property);
    Bean toAdd = (Bean) dia.open();
    if (null != toAdd) {
      ((Collection) property.get()).add(PointerFactory.newPointer(toAdd));
      saveObject();
      repaintTable();
    }

  }

  @Override
  protected void repaintTable() {
    tblTable.removeAll();
    for (Pointer<?> pointer : (Collection <? extends Pointer>) collectionToEdit) {
      TableItem ti = new TableItem(tblTable, SWT.NONE);
      ti.setText(pointer.asString());
      ti.setData(pointer);
    }
  }

  @Override
  protected void btnRemovedPressed(SelectionEvent e) {
    if (tblTable.getSelection().length == 0) {
      return;
    }
    collectionToEdit.remove(tblTable.getSelection()[0].getData());
    repaintTable();
    saveObject();
  }

  @Override
  public void updateEditors(int selectionIndex) {
    // this editor has no sub editors.

  }


}
