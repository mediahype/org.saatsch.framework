package org.saatsch.framework.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.model.JmmoFile;
import org.saatsch.framework.jmmo.data.editor.MainGui;
import org.saatsch.framework.jmmo.data.editor.ui.dialog.ReferenceTargetSelectionDialog;
import org.saatsch.framework.jmmo.data.editor.ui.dialog.images.ImagesWindow;
import org.saatsch.framework.jmmo.data.editor.ui.types.AbstractEditorComposite;
import org.saatsch.framework.base.swt.OpenableDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;

/**
 * edits (the target of) a {@link Pointer} .TODO: currently one cannot reset the reference back to
 * <code>null</code>.
 * 
 * @author saatsch
 *
 */
public class PointerEditor extends AbstractEditorComposite {

  private Text txtContent;

  public PointerEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, SWT.NONE, objectToEdit);
    txtContent = new Text(this, SWT.BORDER);

    txtContent.setBounds(10, 20, 230, 19);
    txtContent.setEnabled(false);
    
    Composite composite = new Composite(this, SWT.NONE);
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));
    composite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
    
        Button btnSelect = new Button(composite, SWT.NONE);
        btnSelect.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            selectObject();
          }
        });
        btnSelect.setText("Select");
        
        Button btnGo = new Button(composite, SWT.NONE);
        btnGo.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            go();
          }

        });
        btnGo.setText("Go");
        
        Button btnReset = new Button(composite, SWT.NONE);
        btnReset.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            resetPointer();
          }
        });
        btnReset.setToolTipText("Reset");
        btnReset.setText("-");

    fillContents();

  }

  private void fillContents() {

    @SuppressWarnings("rawtypes")
    Pointer pointer = (Pointer) property.get();

    if (null != pointer) {

      txtContent.setText(pointer.asString());

    } else {
      txtContent.setText("");
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void selectObject() {
    
    OpenableDialog diag = null; 
    // open select image dialog, if it is an image.
    Class<?> pointerType = PropertyUtil.getPointerType(property);
    if (pointerType.equals(JmmoFile.class)) {
      diag = new ImagesWindow(getShell());
    } else {
      diag = new ReferenceTargetSelectionDialog(getShell(), property);  
    }
    
    
    Object result = diag.open();
    if (null != result) {

      Pointer pointer = (Pointer) property.get();
      pointer.setTargetCoodinate(PropertyUtil.getPointerType(property),
          (String) PropertyUtil.getAppIdProperty((Bean) result).get());

      property.set(pointer);
      saveObject();
      fillContents();
    }
  }
  
  private void resetPointer() {
    Pointer pointer = (Pointer) property.get();
    pointer.setTargetCoodinate(pointer.getBaseClass(), null);
    saveObject();
    fillContents();
  }

  /**
   * try to go to the target tab and select the target object. Do nothing if the pointer is invalid
   * 
   */
  private void go() {
    Pointer pointer = (Pointer) property.get();
    if (pointer.isValid()) {
      JmmoContext.getBean(MainGui.class).selectObject(pointer);      
    }
  }


}
