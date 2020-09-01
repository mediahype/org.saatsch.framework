package de.osrg.tools.apiclient.ui;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.osrg.tools.apiclient.ApiClientUserData;

public class CmdNewCall extends SelectionAdapter {

  private ApiClientMainUI client;

  public CmdNewCall(ApiClientMainUI apiClientMainUI) {
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
