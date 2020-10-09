package org.saatsch.framework.base.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * a dialog that displays a text (in fixed width)
 * 
 * @author saatsch
 *
 */
public class TextDisplayDialog extends Dialog {

  protected Object result;
  protected Shell shell;
  private String textContent;

  /**
   * Create the dialog.
   * @param parent
   * @param style
   */
  public TextDisplayDialog(Shell parent, String textContent) {
    super(parent, SWT.BORDER|SWT.CLOSE|SWT.RESIZE);
    setText("Text");
    this.textContent = textContent;
  }

  /**
   * Open the dialog.
   * @return the result
   */
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
    return result;
  }

  /**
   * Create contents of the dialog.
   */
  private void createContents() {
    shell = new Shell(getParent(), getStyle());
    shell.setSize(450, 300);
    shell.setText(getText());
    shell.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    Text txtText = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    txtText.setFont(SWTResourceManager.getFont("Courier New", 8, SWT.NORMAL));
    txtText.setText(textContent);
  }
  

}
