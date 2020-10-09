package org.saatsch.framework.jmmo.tools.apiclient.ui.scripts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.saatsch.framework.base.serializing.SerializerFactory;
import org.saatsch.framework.base.simplescript.OpenableEditor;
import org.saatsch.framework.base.swt.DialogUtil;
import org.saatsch.framework.jmmo.tools.apiclient.model.MethodCallVO;
import org.saatsch.framework.jmmo.tools.apiclient.ui.AbstractMethodUI;

/**
 * dialog that edits a MethodCallVO
 * 
 * @author saatsch
 *
 */
public class DialogCallEdit extends Dialog implements OpenableEditor<MethodCallVO> {

  private Shell shell;
  private MethodCallVO toEdit;
  private AbstractMethodUI methodUi;
  
  public DialogCallEdit(Shell parent) {
    super(parent);
  }

  /**
   * Open the dialog.
   * 
   * @return the result
   */
  public MethodCallVO open() {
    
    // if there is no item set to edit, we request one.
    if (null == toEdit) {
      DialogCallSelect diag = new DialogCallSelect(getParent());
      MethodCallVO call = diag.open();
      if (null == call) {
        return null;
      }
      toEdit = SerializerFactory.newXmlSerializer().copyObject(call, MethodCallVO.class);
    }
    
    
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
    shell.setSize(367, 477);
    shell.setText("Method Call");    
    shell.setLayout(new GridLayout(1, false));
    
    methodUi = new AbstractMethodUI(shell, SWT.NONE);
    methodUi.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    
    methodUi.setMethod(toEdit.getMethod());
    methodUi.setArgs(toEdit.getCallParameters());
    
    Button btnOk = new Button(shell, SWT.NONE);
    btnOk.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        toEdit.setCallParameters(methodUi.getArgs());
        shell.dispose();
      }
    });
    btnOk.setText("OK");
    
    
    DialogUtil.center(shell, getParent());
    
  }

  @Override
  public void setToEdit(MethodCallVO toEdit) {
    this.toEdit = toEdit;
  }


}
