package org.saatsch.framework.jmmo.tools.apiclient.ui;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import org.saatsch.framework.jmmo.tools.apiclient.ApiClientUserData;

public class CmdNewCall extends SelectionAdapter {

  private ApiClientMainC client;

  public CmdNewCall(ApiClientMainC apiClientMainUI) {
    this.client = apiClientMainUI;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    client.getCurrentlySelectedMethod().ifPresent(m -> {
      ApiClientUserData.getUserData().addMethodCall(m.getDeclaredIn(), m.getName());
      client.updateCalls();
    });


  }



}
