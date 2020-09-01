package de.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.data.editor.ui.types.AbstractEditorComposite;

public class ImageEditor extends AbstractEditorComposite {

  public ImageEditor(Composite parent, Property<Object> fieldDefinition, Bean objectToEdit) {
    super(parent, fieldDefinition, SWT.NONE, objectToEdit);
    
    Button btnSelect = new Button(this, SWT.NONE);
    btnSelect.setText("Select...");
  }
  
}
