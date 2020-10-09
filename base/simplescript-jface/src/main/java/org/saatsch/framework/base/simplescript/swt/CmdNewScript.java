package org.saatsch.framework.base.simplescript.swt;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CmdNewScript extends SelectionAdapter {

  private ScriptsUI client;
  private ScriptsModel model;

  public CmdNewScript(ScriptsUI scriptsUI, ScriptsModel model) {
    this.client = scriptsUI;
    this.model = model;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {

    model.addScript("New Script");
    client.redrawScripts(model.getScripts());
    
  }
  
}
