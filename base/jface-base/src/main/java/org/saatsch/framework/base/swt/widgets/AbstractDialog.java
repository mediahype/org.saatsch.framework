package org.saatsch.framework.base.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * this is rather a template to be copied/pasted to create a new dialog. It is not a usable class.
 * 
 * @author saatsch
 *
 */
public final class AbstractDialog extends Dialog {

  protected Shell shell;

  /**
   * 
   */
  protected Object dialogReturnValue;

  public AbstractDialog(Shell parent) {
    super(parent);
  }


  /**
   * Open the dialog.
   * 
   */
  public Object open() {
    createContent();
    shell.open();
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

  }

  /**
   * a dialog is closed by disposing it's shell.
   */
  private void close() {
    shell.dispose();
  }


}
