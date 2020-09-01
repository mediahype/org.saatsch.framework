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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.osrg.base.swt.DialogUtil;

/**
 * the login dialog
 * 
 * @author saatsch
 *
 */
public class LoginDialog extends Dialog {

  private Shell shell;

  /**
   * 
   */
  protected Credentials dialogReturnValue;
  private Text txtUser;
  private Text txtPass;

  public LoginDialog(Shell parent) {
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


  private void createContent() {
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

    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));
    composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

    Button btnOk = new Button(composite, SWT.NONE);
    btnOk.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ok();
      }
    });
    btnOk.setText("OK");



  }


  public void ok() {
    dialogReturnValue = new Credentials(txtUser.getText(), txtPass.getText());
    shell.dispose();
  }

}
