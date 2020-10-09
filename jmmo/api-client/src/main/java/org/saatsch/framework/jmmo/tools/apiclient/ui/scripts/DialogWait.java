package org.saatsch.framework.jmmo.tools.apiclient.ui.scripts;

import org.saatsch.framework.base.simplescript.OpenableEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import org.saatsch.framework.base.swt.DialogUtil;
import org.saatsch.framework.jmmo.tools.apiclient.model.WaitVO;

/**
 * dialog that edits or creates a WaitVO
 * 
 * @author saatsch
 *
 */
public class DialogWait extends Dialog implements OpenableEditor<WaitVO> {

  private Shell shell;
  private WaitVO toEdit;
  private Spinner millis;

  public DialogWait(Shell parent) {
    super(parent);
  }

  /**
   * Open the dialog.
   * 
   * @return the result
   */
  public WaitVO open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return toEdit;
  }

  private void createContents() {
    shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.PRIMARY_MODAL);
    shell.setSize(300, 103);
    shell.setText("Wait");
    shell.setLayout(new GridLayout(4, false));
    
    Label lblWaitFor = new Label(shell, SWT.NONE);
    lblWaitFor.setText("Wait for:");
    
    millis = new Spinner(shell, SWT.BORDER);
    millis.setMaximum(100000);
    GridData gd_millis = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
    gd_millis.widthHint = 68;
    millis.setLayoutData(gd_millis);
    
    Label lblMilliseconds = new Label(shell, SWT.NONE);
    lblMilliseconds.setText("milliseconds");
    new Label(shell, SWT.NONE);
    new Label(shell, SWT.NONE);
    new Label(shell, SWT.NONE);
    new Label(shell, SWT.NONE);
    
    Button btnOk = new Button(shell, SWT.NONE);
    btnOk.addListener(SWT.Selection, event -> ok() );
    btnOk.setText("OK");
    
    fillContents();
    
    DialogUtil.center(shell, getParent());
    DialogUtil.execOnEnter(() -> ok(), millis);
    
  }

  /**
   * sets the {@link WaitVO} to be edited by this dialog. Call this method <em>before</em> calling {@link #open()}.
   * 
   * @param toEdit
   */
  public void setToEdit (WaitVO toEdit) {
    this.toEdit = toEdit;
  }
  
  private void ok() {
    if (toEdit == null) {
      toEdit = new WaitVO();
    }
    toEdit.setWaitMillis(millis.getSelection());
    shell.dispose();
  }

  private void fillContents() {
    if (toEdit != null && toEdit.getWaitMillis() != null) {
      millis.setSelection(toEdit.getWaitMillis());
    }
    
  }
  
}
