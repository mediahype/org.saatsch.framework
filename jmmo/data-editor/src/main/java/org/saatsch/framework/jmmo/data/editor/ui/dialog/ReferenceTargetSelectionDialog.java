package org.saatsch.framework.jmmo.data.editor.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.FilterableTreeComposite;
import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.PointerFilterableTreeComposite;
import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.PropertyFilterableTreeComposite;
import org.saatsch.framework.base.swt.OpenableDialog;

public class ReferenceTargetSelectionDialog extends OpenableDialog {

  private Shell shell;
  private Property<Object> property;
  private Text txtSelection;
  private FilterableTreeComposite cmpList;
  private Object ret;

  public ReferenceTargetSelectionDialog(Shell parent, Property<Object> property) {
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
    shell.setSize(401, 535);
    shell.setText(property.name());
    shell.setLayout(new GridLayout(2, false));

    Label lblSelection = new Label(shell, SWT.NONE);
    lblSelection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblSelection.setText("Selection:");

    txtSelection = new Text(shell, SWT.BORDER);
    txtSelection.setEnabled(false);
    txtSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Composite cmpAux = new Composite(shell, SWT.NONE);
    cmpAux.setLayout(new FillLayout(SWT.HORIZONTAL));

    if (PropertyUtil.isPointer(property)) {
      cmpList = new PointerFilterableTreeComposite(cmpAux, property);
    } else {
      cmpList = new PropertyFilterableTreeComposite(cmpAux, property);
    }
    cmpList.refresh();
    
    cmpAux.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));

    Button btnOk = new Button(composite, SWT.NONE);
    btnOk.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        okPressed();
      }
    });
    btnOk.setText("&OK");

    Button btnCancel = new Button(composite, SWT.NONE);
    btnCancel.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        shell.close();
      }
    });
    btnCancel.setText("Cancel");
    new Label(shell, SWT.NONE);
    
    fillContents();

  }

  private void fillContents() {

    
    
  }


  private void okPressed() {
    ret = cmpList.getCurrentlySelected();
    shell.dispose();

  }
}
