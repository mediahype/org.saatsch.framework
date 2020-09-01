package de.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.api.Pointer;
import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.api.model.JmmoImage;
import de.jmmo.data.editor.MainGui;
import de.jmmo.data.editor.ui.dialog.ReferenceTargetSelectionDialog;
import de.jmmo.data.editor.ui.dialog.images.ImagesWindow;
import de.jmmo.data.editor.ui.types.AbstractEditorComposite;
import de.osrg.base.swt.OpenableDialog;
import org.eclipse.swt.widgets.Label;
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
            Pointer pointer = (Pointer) property.get();
            //JmmoContext.getBean(MainGui.class).switchToTypeTab(pointer.getBaseClass());
            JmmoContext.getBean(MainGui.class).selectObject(pointer);
          }
        });
        btnGo.setText("Go");

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
    if (pointerType.equals(JmmoImage.class)) {
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

}
