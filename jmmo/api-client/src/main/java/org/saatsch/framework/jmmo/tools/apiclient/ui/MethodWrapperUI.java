package org.saatsch.framework.jmmo.tools.apiclient.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.saatsch.framework.base.swt.widgets.TextDisplayDialog;
import org.saatsch.framework.jmmo.tools.apiclient.model.MethodCallVO;



/**
 * this UI manages the {@link AbstractMethodUI} that it wraps.
 * 
 * @author saatsch
 *
 */
public class MethodWrapperUI extends Composite {


  private Composite cmpContent;
  private AbstractMethod currentAbstractMethod;
  private MethodCallVO currentMethodCall;
  private AbstractMethodUI cmpMethodUI;
  private Composite cmpButtons;
  private Button btnSave;
  private Button btnExecute;


  public MethodWrapperUI(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new GridLayout(1, false));

    cmpContent = new Composite(this, SWT.NONE);
    cmpContent.setLayout(new GridLayout(1, false));
    cmpContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    cmpMethodUI = new AbstractMethodUI(cmpContent, SWT.NONE);
    cmpMethodUI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    cmpButtons = new Composite(this, SWT.NONE);
    cmpButtons.setLayout(new GridLayout(2, false));

    btnExecute = new Button(cmpButtons, SWT.NONE);
    btnExecute.setEnabled(false);
    btnExecute.setSize(52, 25);
    btnExecute.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        Object ret = null;
        try {
          ret = currentAbstractMethod.execute(cmpMethodUI.getArgs());
        } catch (Exception ex) {
          displayException(ex);
        }
        if (null != ret) {
          TextDisplayDialog diag = new TextDisplayDialog(getShell(), ret.toString());
          diag.open();
        }
      }

    });
    btnExecute.setText("Execute");

    btnSave = new Button(cmpButtons, SWT.NONE);
    btnSave.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        saveMethodCall();
      }
    });
    btnSave.setEnabled(false);
    btnSave.setBounds(0, 0, 75, 25);
    btnSave.setText("Save");

  }

  private void saveMethodCall() {
    currentMethodCall.setCallParameters(cmpMethodUI.getArgs());
  }

  /**
   * setting the method removes the old one, creates a new one (incl. UI) and then sets it.
   * 
   * @param method
   */
  public void setMethod(AbstractMethod method) {
    disposeOldMethod();
    cmpMethodUI = new AbstractMethodUI(cmpContent, SWT.NONE, method.getMethod());
    this.currentAbstractMethod = method;
    this.currentMethodCall = null;
    btnExecute.setEnabled(true);
    cmpContent.layout();
  }


  public void setMethod(MethodCallVO method) {
    disposeOldMethod();
    cmpMethodUI = new AbstractMethodUI(cmpContent, SWT.NONE, method.getMethod());
    this.currentAbstractMethod = null;
    this.currentMethodCall = method;
    cmpMethodUI.setArgs(method.getCallParameters());
    btnSave.setEnabled(true);
    cmpContent.layout();
  }


  private void disposeOldMethod() {
    if (null == cmpMethodUI) {
      return;
    }

    for (Control cntrl : cmpMethodUI.getChildren()) {
      cntrl.dispose();
    }
    cmpMethodUI.dispose();
    cmpMethodUI = null;

    btnExecute.setEnabled(false);
    btnSave.setEnabled(false);


  }

  private void displayException(Exception ex) {
    StringBuilder sb = new StringBuilder();
    sb.append("AN EXCEPTION HAS OCCURED: \n");
    sb.append(ex);
    TextDisplayDialog diag = new TextDisplayDialog(getShell(), sb.toString());
    diag.open();
  }
}
