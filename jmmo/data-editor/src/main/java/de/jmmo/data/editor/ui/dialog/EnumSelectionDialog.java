package de.jmmo.data.editor.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.joda.beans.Property;

import de.jmmo.data.api.PropertyUtil;

/**
 * this dialog is called from the EnumListEditor and as such, the given property is a List (or a
 * Set)
 * 
 * @author saatsch
 *
 */
public class EnumSelectionDialog extends Dialog {
  private Shell shell;
  private Property<Object> property;
  private Object ret;
  private Combo cmbList;
  private Class genericClass;

  public EnumSelectionDialog(Shell parent, Property<Object> property) {
    super(parent, SWT.CLOSE | SWT.RESIZE | SWT.PRIMARY_MODAL);
    this.property = property;
  }

  public Object open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return ret;

  }

  private void createContents() {
    shell = new Shell(getParent(), getStyle());
    shell.setSize(393, 114);
    shell.setText(property.name());
    shell.setLayout(new GridLayout(1, false));

    cmbList = new Combo(shell, SWT.NONE);
    cmbList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));

    Button btnOk = new Button(composite, SWT.NONE);
    btnOk.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ret = PropertyUtil.getEnumInstance(cmbList.getText(), genericClass);
        shell.dispose();
      }
    });
    btnOk.setText("OK");

    Button btnCancel = new Button(composite, SWT.NONE);
    btnCancel.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ret = null;
        shell.dispose();
      }
    });
    btnCancel.setText("Cancel");

    fillContents();

  }

  private void fillContents() {

    genericClass = PropertyUtil.genericClass(property.metaProperty());
    for (Object o : genericClass.getEnumConstants()) {
      cmbList.add(o.toString());
    }

  }

}
