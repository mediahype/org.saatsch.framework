package de.jmmo.data.editor.ui.dialog;

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
 * AddFriendDialog prompts for a character name and sends an addFriend Request to the server.
 * 
 * @author saatsch
 *
 */
public final class AddStringDialog extends Dialog {

  protected Shell shlAddString;

  private String dialogReturnValue;
  private Text txtString;

  public AddStringDialog(Shell parent) {
    super(parent);
  }


  /**
   * Open the dialog.
   * 
   */
  public String open() {
    createContent();
    shlAddString.open();
    DialogUtil.center(shlAddString, getParent());
    shlAddString.layout();
    Display display = getParent().getDisplay();
    while (!shlAddString.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return dialogReturnValue;
  }


  private void createContent() {
    shlAddString = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    shlAddString.setSize(450, 98);
    shlAddString.setText("Add String");
    shlAddString.setLayout(new GridLayout(2, false));

    Label lblFriendName = new Label(shlAddString, SWT.NONE);
    lblFriendName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblFriendName.setText("String:");

    txtString = new Text(shlAddString, SWT.BORDER);
    txtString.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    DialogUtil.execOnEnter(this::ok, txtString);
    
    Composite composite = new Composite(shlAddString, SWT.NONE);
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

  /**
   * a dialog is closed by disposing it's shell.
   */
  private void ok() {
    dialogReturnValue = txtString.getText();
    shlAddString.dispose();
  }


}
