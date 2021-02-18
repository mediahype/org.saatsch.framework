package org.saatsch.framework.base.swt;

import java.awt.GraphicsEnvironment;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.saatsch.framework.base.swt.safeapploop.SafeAppLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Class for Tests that wish to display a dialog.
 * Display will not start in headless environments, effectively skipping the test.
 * 
 */
public abstract class MinimalParentShell {

  private static final Logger LOG = LoggerFactory.getLogger(MinimalParentShell.class);
  
  protected Composite mainComposite;

  private static Shell shell;

  public Void open() {

    if (GraphicsEnvironment.isHeadless()) {
      LOG.info(this.getClass().getName() + " is skipped, because we are in headless mode.");
      return null;
    }
    
   
    Display display = Display.getDefault();
    shell = createContents();

    // initialize the implementor.
    Openable dialog = init(shell);

    shell.open();
    shell.layout();

    if (null != dialog){
      dialog.open();
      shell.dispose();
    }else {
      // keep this open.
      SafeAppLoop.run(shell, display);
    }
    
    return null;
    
  }

  private Shell createContents() {
    shell = new Shell();
    shell.setSize(796, 533);
    shell.setText("Minimal Parent Shell");
    shell.setLayout(new FillLayout(SWT.HORIZONTAL));
    mainComposite = new Composite(shell, SWT.NONE);
    mainComposite.setLayout(new FillLayout());
    return shell;
  }

  /**
   * users initialize what they want to display here and return it
   * 
   * @return the desired Dialog to display or null if no dialog should be displayed.
   */
  protected abstract Openable init(Shell parentShell) ;


}
