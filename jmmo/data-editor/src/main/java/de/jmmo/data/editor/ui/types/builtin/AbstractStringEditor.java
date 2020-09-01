package de.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.data.editor.ui.types.AbstractEditorComposite;

public class AbstractStringEditor extends AbstractEditorComposite {
  
  public AbstractStringEditor(Composite parent, Property<Object> stringProperty, int style,
      Bean objectToEdit) {
    super(parent, stringProperty, style, objectToEdit);
    
  }

  
}
