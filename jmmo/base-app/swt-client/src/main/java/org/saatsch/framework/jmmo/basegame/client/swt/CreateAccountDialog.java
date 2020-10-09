package org.saatsch.framework.jmmo.basegame.client.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.saatsch.framework.jmmo.basegame.client.ServerConnectionApi;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.base.swt.DialogUtil;

/**
 * the login dialog
 * 
 * @author saatsch
 *
 */
public class CreateAccountDialog extends CredentialsDialog {


  public CreateAccountDialog(Shell parent) {
    super(parent);
  }


  /**
   * Open the dialog.
   * 
   */
  public Credentials open() {
    createContent();
    shell.open();
    DialogUtil.center(shell, getParent());
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return dialogReturnValue;
  }


  protected void createContent() {
    super.createContent();
    shell.setText("Create Account");

    Button btnOk = new Button(cmpButtons, SWT.NONE);
    btnOk.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        JmmoContext.getBean(ServerConnectionApi.class).createAccount(txtUser.getText(), txtPass.getText());
        shell.dispose();
      }
    });
    btnOk.setText("Create Account");

  }


}
