package de.jmmo.basegame.client.swt;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
public class LoginDialog extends CredentialsDialog {





  public LoginDialog(Shell parent) {
    super(parent);
  }


  /**
   * Open the dialog.
   * 
   * @return the entered credentials, or empty if no credentials where provided (e.g. in case dialog was closed).
   * 
   */
  public Optional<Credentials> open() {
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
    return Optional.ofNullable(dialogReturnValue);
  }


  protected void createContent() {
    super.createContent();
    
    shell.setText("Login");

    
    Button btnCreateAccount = new Button(cmpButtons, SWT.NONE);
    btnCreateAccount.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        showCreateAccount();
      }
    });
    btnCreateAccount.setText("Create Account...");
    
    createOkButton();
    

  }

  private void showCreateAccount() {
    CreateAccountDialog diag = new CreateAccountDialog(shell);
    diag.open();
    
  }



}
