package org.saatsch.framework.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.ui.types.AbstractEditorComposite;

public class EnumEditor extends AbstractEditorComposite {

  private Enum enumToEdit;
  private Combo cmbList;

  public EnumEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, SWT.NONE, objectToEdit);

    cmbList = new Combo(this, SWT.NONE);
    cmbList.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        saveProperty();
      }

    });
    cmbList.setBounds(10, 24, 241, 23);
    
    if (!property.metaProperty().style().isWritable()) {
      cmbList.setEnabled(false);
    }
    
    enumToEdit = (Enum) property.get();

    fillContents();

  }

  private void fillContents() {

    for (Object o : PropertyUtil.getEnumConstants(property)) {
      cmbList.add(o.toString());
    }

    cmbList.select(cmbList.indexOf(property.get().toString()));
    cmbList.pack();
  }

  private void saveProperty() {
    for (Object o : PropertyUtil.getEnumConstants(property)) {
      if (o.toString().equals(cmbList.getText())) {
        property.set(PropertyUtil.getEnumInstance(o.toString(), enumToEdit.getDeclaringClass()));
        saveObject();
      }
    }

  }

}
