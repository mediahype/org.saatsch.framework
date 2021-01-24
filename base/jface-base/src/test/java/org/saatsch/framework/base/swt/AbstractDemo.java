package org.saatsch.framework.base.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractDemo {

  protected Shell shell;
  protected Composite content;
  
  
  /**
   * @wbp.parser.entryPoint
   */
  public void open() {
    Display display = Display.getDefault();
    createFrame();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }


  private void createFrame() {
    shell = new Shell();
    shell.setSize(474, 306);
    shell.setText("AbstractDemo");
    shell.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    content = new Composite(shell, SWT.NONE);
    content.setLayout(new FillLayout(SWT.HORIZONTAL));
    
  }


  protected abstract void createContents();

  
  
  
}
