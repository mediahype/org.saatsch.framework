package de.jmmo.basegame.client.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.osrg.base.swt.DialogUtil;

/**
 * an abstract credentials dialog
 * 
 * @author saatsch
 *
 */
public abstract class CredentialsDialog extends Dialog {

  protected Shell shell;

  /**
   * 
   */
  protected Credentials dialogReturnValue;
  
  protected Text txtUser;
  protected Text txtPass;

  protected Composite cmpButtons;

  public CredentialsDialog(Shell parent) {
    super(parent);
  }


  protected void createContent() {
    shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    shell.setSize(294, 128);
    shell.setLayout(new GridLayout(2, false));
    shell.setText("Login");

    Label lblUser = new Label(shell, SWT.NONE);
    lblUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblUser.setText("user");

    txtUser = new Text(shell, SWT.BORDER);
    txtUser.setText("user");
    txtUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    DialogUtil.execOnEnter(this::ok, txtUser);

    Label lblPass = new Label(shell, SWT.NONE);
    lblPass.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblPass.setText("pass");

    txtPass = new Text(shell, SWT.BORDER);
    txtPass.setEchoChar('*');
    txtPass.setText("pass");
    txtPass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    DialogUtil.execOnEnter(this::ok, txtPass);

    cmpButtons = new Composite(shell, SWT.NONE);
    cmpButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
    cmpButtons.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

  }

  /**
   * execute when user input is finished. Closes the dialog.
   */
  protected void ok() {
    dialogReturnValue = new Credentials(txtUser.getText(), txtPass.getText());
    shell.dispose();
  }
  
  protected void createOkButton() {
    Button btnOk = new Button(cmpButtons, SWT.NONE);
    btnOk.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ok();
      }
    });
    btnOk.setText("OK");
  }


}
