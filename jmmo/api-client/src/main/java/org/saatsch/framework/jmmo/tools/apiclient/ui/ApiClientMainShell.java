package org.saatsch.framework.jmmo.tools.apiclient.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.saatsch.framework.base.swt.safeapploop.SafeAppLoop;

public class ApiClientMainShell {
  
  private static Shell shlApiClient;
  


  public static Shell getShell() {
    return shlApiClient;
  }

  /**
   * Open the window.
   * 
   * @wbp.parser.entryPoint
   */
  public void open() {
    
    
    Display display = Display.getDefault();
    createContents();
    shlApiClient.open();
    shlApiClient.layout();
  
    SafeAppLoop.run(shlApiClient, display);

  }



  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shlApiClient = new Shell();
    shlApiClient.setSize(783, 518);
    shlApiClient.setText("API Client");
    shlApiClient.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    
    ApiClientMainC cmpMain = new ApiClientMainC(shlApiClient, SWT.NONE);
    cmpMain.setLayout(new FillLayout(SWT.HORIZONTAL));
  
  }










}
