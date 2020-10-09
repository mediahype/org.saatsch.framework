package org.saatsch.framework.base.swt.widgets.logintercept;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * demo for the logintercept widget. This only works, when the classpath uses slf4j-simple (which is the case in this test scope).
 * 
 * note that configuring the simplelogger.properties file (which sits in the resources folder) configures the output of this app. 
 * 
 * @author saatsch
 *
 */
public class LogInterceptDemo implements Appending{

  private static final Logger LOG = LoggerFactory.getLogger(LogInterceptDemo.class);
  
  protected Shell shell;
  private LogComposite logWidget;

  /**
   * Launch the application.
   * @param args
   */
  public static void main(String[] args) {
    
    try {
      LogInterceptDemo demo = new LogInterceptDemo();
      LogInterceptorFactory.initLogging(demo);
      demo.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Open the window.
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(865, 279);
    shell.setText("SWT Application");
    shell.setLayout(new GridLayout(1, false));
    
    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));
    
    Button btnInfo = new Button(composite, SWT.NONE);
    btnInfo.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        LOG.info("test INFO log.");
      }
    });
    btnInfo.setText("Log Info");
    
    Button btnLogWarn = new Button(composite, SWT.NONE);
    btnLogWarn.setText("Log Warn");
    btnLogWarn.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        LOG.warn("test WARN log.");
      }
    });
    
    Button btnLogerror = new Button(composite, SWT.NONE);
    btnLogerror.setText("LogError");
    btnLogerror.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        LOG.error("test ERROR log.");
      }
    });
    
    
    
    logWidget = new LogComposite(shell);
    logWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

  }

  @Override
  public void append(String toAppend) {
    logWidget.append(toAppend);
    
  }
}
