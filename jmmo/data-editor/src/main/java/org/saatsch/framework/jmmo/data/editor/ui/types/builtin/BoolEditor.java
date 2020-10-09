package org.saatsch.framework.jmmo.data.editor.ui.types.builtin;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.editor.ui.types.AbstractEditorComposite;

public class BoolEditor extends AbstractEditorComposite {

  private final Button button;


  public BoolEditor(Composite parent, Property<Object> fieldDefinition, Bean objectToEdit) {
    super(parent, fieldDefinition, SWT.NONE, objectToEdit);

    button = new Button(this, SWT.CHECK);
    Boolean selected = (Boolean) fieldDefinition.get();
    button.setSelection(selected);
    button.setBounds(10, 23, 105, 16);

    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        fieldDefinition.set(button.getSelection());
        saveObject();
      }
    });

  }


}
