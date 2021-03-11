package org.saatsch.framework.base.swt.snippets;

import org.saatsch.framework.base.swt.safeapploop.SafeAppLoop;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Demonstrates the use of {@link SafeAppLoop}
 */
public class SafeAppLoopDemo {

  private Shell shell;

  /**
   * Launch the application.
   * @param args
   */
  public static void main(String[] args) {
    
      new SafeAppLoopDemo().open();

  }

  /**
   * Open the window.
   */
  public void open() {

    Display display = Display.getDefault();

    createContents();

    Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
      @Override
      public void run() {
        shell.open();
        shell.layout();

        SafeAppLoop.run(shell, display);

      }
    });


    display.dispose();
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(865, 279);
    shell.setText(getClass().getName());
    shell.setLayout(new GridLayout(1, false));
    
    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));
    
    Button btnRuntimeException = new Button(composite, SWT.NONE);
    btnRuntimeException.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        throw new RuntimeException("RuntimeExceptionMessage");
      }
    });
    btnRuntimeException.setText("Throw RuntimeException");


  }

  
  
}
